package main

import (
    "fmt"

    "github.com/hyperledger/fabric/core/chaincode/shim"
    "github.com/hyperledger/fabric/protos/peer"
)

// SimpleAsset은 간단한 체인 코드를 구현하여 자산을 관리합니다.
type SimpleAsset struct {
}

// 초기화를 위해 체인 코드 인스턴스화 중에 Init가 호출됩니다.
// 데이터. chaincode 업그레이드는이 기능을 호출하여 재설정합니다.
// 데이터를 이전합니다.
func (t *SimpleAsset) Init(stub shim.ChaincodeStubInterface) peer.Response {
    // 거래 제안서에서 args 가져 오기
    args := stub.GetStringArgs()
    if len(args) != 2 {
            return shim.Error("Incorrect arguments. Expecting a key and a value")
    }

    // stub.PutState ()를 호출하여 여기에 모든 변수 또는 에셋을 설정합니다.

    // 원장에 키와 값을 저장합니다.
    err := stub.PutState(args[0], []byte(args[1]))
    if err != nil {
            return shim.Error(fmt.Sprintf("Failed to create asset: %s", args[0]))
    }
    return shim.Success(nil)
}

// 호출은 chaincode에서 트랜잭션 당 호출됩니다. 각 거래는
// Init 함수에 의해 생성 된 자산의 'get'또는 'set'중 하나입니다. 세트
// 메소드는 새로운 키 - 값 쌍을 지정하여 새 자산을 만들 수 있습니다.
func (t *SimpleAsset) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
    // 트랜잭션 제안서에서 함수와 arg를 추출합니다.
    fn, args := stub.GetFunctionAndParameters()

    var result string
    var err error
    if fn == "set" {
            result, err = set(stub, args)
    } else { // fn이 nil이더라도 'get'이라고 가정합니다.

            result, err = get(stub, args)
    }
    if err != nil {
            return shim.Error(err.Error())
    }

    // 결과 페이로드를 성공 페이로드로 반환합니다.
    return shim.Success([]byte(result))
}

// 원장에 자산을 저장합니다 (키와 값 모두). 키가 존재하면,
// 새 값으로 값을 대체합니다.
func set(stub shim.ChaincodeStubInterface, args []string) (string, error) {
    if len(args) != 2 {
            return "", fmt.Errorf("Incorrect arguments. Expecting a key and a value")
    }

    err := stub.PutState(args[0], []byte(args[1]))
    if err != nil {
            return "", fmt.Errorf("Failed to set asset: %s", args[0])
    }
    return args[1], nil
}

// Get은 지정된 자산 키의 값을 반환합니다.
func get(stub shim.ChaincodeStubInterface, args []string) (string, error) {
    if len(args) != 1 {
            return "", fmt.Errorf("Incorrect arguments. Expecting a key")
    }

    value, err := stub.GetState(args[0])
    if err != nil {
            return "", fmt.Errorf("Failed to get asset: %s with error: %s", args[0], err)
    }
    if value == nil {
            return "", fmt.Errorf("Asset not found: %s", args[0])
    }
    return string(value), nil
}

// main 함수는 인스턴스화하는 동안 컨테이너에서 chaincode를 시작합니다.
func main() {
    if err := shim.Start(new(SimpleAsset)); err != nil {
            fmt.Printf("Error starting SimpleAsset chaincode: %s", err)
    }
}
