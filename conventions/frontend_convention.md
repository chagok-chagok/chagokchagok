# [FE] 코드컨벤션

# 리액트 컨벤션 정리

### **1. NAMING CONVENTIONS**

1. components 이름은 **`Pascal case`**로 작성해라.
    
    pascal case : 첫 단어를 대문자로 시작하는 표기법
    
    `Header.js Footer.jsx MainBanner.js BlogList.jsx`
    
2. Non-components 이름은 **`Camel case`**로 작성해라.
    
    Camel case : 띄어쓰기 대신 대문자로 단어를 구분하는 표기 방식
    
    `myUtilityFile.js cookieHelper.js fetchApi.js`
    
3. Unit test 파일명은 대상 파일명과 동일하게 작성해라.
    
    `MainBanner.js MainBanner.test.js BlogList.js BlogList.test.js`
    
4. 속성명은 **`Camel case`**로 작성해라.
    
    `className
    <div className=""></div>
    
    onClick, onSubmit
    <div onClick="{}" onSubmit="{}"></div>`
    
5. inline 스타일은 **`Camel case`**로 작성해라.
    
    `<div style={{ fontSize: "1rem", fontWeight: "700" }}></div>`
    

### **2. BUG AVOIDANCE**

1. **`null`** 또는 **`undefined`**일 수 있는 값은 optional chaining 연산자 (?.)를 사용한다.
    
    `obj?.prop
    obj?.[expr]
    arr?.[index]
    func?.(args)`
    
2. 전달된 매개변수가 유효한지 확인하기 위해 guard pattern/[prop types](https://www.jondjones.com/frontend/react/react-tutorials/how-to-config-typescript-within-a-react-app/)/[typescript](https://www.jondjones.com/frontend/react/react-tutorials/how-to-config-typescript-within-a-react-app/)를 사용한다.
3. side-effects를 피하기 위해 외부 데이터를 props로 받아서 사용한다.
4. console.log()를 모두 지운다.
5. props는 읽기 전용으로 사용하며, 직접 수정하지 않는다.

### **3. ARCHITECTURE & CLEAN CODE**

1. 유틸리티 파일을 만들어 중복된 코드를 피한다.
2. Presentational 컴포넌트와 Container 컴포넌트를 분리하여 사용한다. [Container 컴포넌트와 Presentational 컴포넌트](https://jeffgukang.github.io/react-native-tutorial/docs/state-tutorial/redux-tutorial/04-container-and-presentational/container-and-presentational-kr.html)
3. 고차 컴포넌트(Higher Order Components, HOC)는 적절하게 사용한다.
4. JS, test, css로 파일을 분리한다.
5. 반복되어 import되는 이름을 줄이기 위해 각 폴더에 index.js 파일을 만든다.

`import { Nav } from "./Nav.js"
import { CookieBanner } from "./CookieBanner.js"

export { Nav, CookieBanner }`

1. 하나의 파일에 하나의 React component만 만든다.
2. 가능하다면 컴포넌트 안에서 함수를 생성하지 않는다.
3. 부모 컴포넌트가 아닌 다른 컴포넌트의 함수를 사용하지 않는다. (의존성 역전을 피한다.)
4. 불필요한 주석을 사용하지 않는다.
5. 현재 화면보다 긴 코드는 더 작은 단위의 코드로 리팩토링 한다.
6. 주석 처리된 코드는 커밋하지 말고 삭제한다.

### **4. ES6**

1. spread 연산자를 사용한다.
2. 구조 분해 할당을 사용한다.
3. let과 const만 사용한다. (var 사용금지)
4. 되도록 화살표 함수를 사용한다.
5. 직접 null을 체크하기 보다 optional chaining 연산자 (?.)를 사용한다.

### **5. TESTING**

1. 테스트를 작성한다.
2. 적정 수준의 테스트 커버리지를 유지한다.
3. 하나의 테스트 파일에서 하나만 테스트 한다.
4. 테스트 코드안에서 로직을 사용하지 않는다.
5. 테스트 클래슽는 하나의 클래스만 테스트 한다.
6. 네트워크, 데이터 베이스와 직접 통신하지말고 가짜 함수를 사용한다.

### **6. CSS**

1. inline css를 사용하지 않는다.
2. 명명 규칙을 지킨다. (네이밍 컨벤션 - bem, SUIT 등)

# 자바스크립트 컨벤션 정리

### let 대신 const이 기본이다

- 모든 참조에는 let 대신 const를 사용하는 것을 기본으로 한다.
- 다만 재할당을 해야 한다면 let을 사용한다.
- (참고) let과 const 모두 블록 스코프이다.

```jsx
// const와 let은 선언된 블록 안에서만 존재합니다.
{
  let a = 1;
  const b = 1;
}
console.log(a); // ReferenceError
console.log(b); // ReferenceError
```

### 객체를 생성할 때는 리터럴 문법을 사용하자

```jsx
// bad
const item = new Object();

// good
const item = {};
```

### 단축구문을 사용하자

lukeSkywalker가 lukeSkywalker를 의미하면 굳이 할당해줄 필요가 없다는 뜻인 것 같다.

```jsx
const lukeSkywalker = 'Luke Skywalker';

// bad
const obj = {
  lukeSkywalker: lukeSkywalker,
};

// good
const obj = {
  lukeSkywalker,
};
```

그리고 이것들은 객체 선언의 시작 부분에 모아주자.

```jsx
const anakinSkywalker = 'Anakin Skywalker';
const lukeSkywalker = 'Luke Skywalker';

// bad
const obj = {
  episodeOne: 1,
  twoJediWalkIntoACantina: 2,
  lukeSkywalker,
  episodeThree: 3,
  mayTheFourth: 4,
  anakinSkywalker,
};

// good
const obj = {
  lukeSkywalker,
  anakinSkywalker,
  episodeOne: 1,
  twoJediWalkIntoACantina: 2,
  episodeThree: 3,
  mayTheFourth: 4,
};
```

### 배열을 생성할 때 리터럴을 구문 사용하자

```jsx
// bad
const items = new Array();

// good
const items = [];
```

### 배열에 직접 값을 할당하지 말고 Array.push을 사용하자

```jsx
const someStack = [];

// bad
someStack[someStack.length] = 'abracadabra';

// good
someStack.push('abracadabra');
```

### 배열을 복사할 때는 배열 전개 구문 `...`을 사용하자

```jsx
// bad
const len = items.length;
const itemsCopy = [];
let i;

for (i = 0; i < len; i += 1) {
  itemsCopy[i] = items[i];
}

// good
const itemsCopy = [...items];
```

### array-like 객체를 배열로 변환할 때는 Array.from을 사용하자

```jsx
const arrLike = { 0: 'foo', 1: 'bar', 2: 'baz', length: 3 };

// bad
const arr = Array.prototype.slice.call(arrLike);

// good
const arr = Array.from(arrLike);
```

### 배열이 여러 줄에 걸쳐 있다면 배열을 연 이후와 닫기 이전에 줄바꿈을 하자

```jsx
// bad
const arr = [
  [0, 1], [2, 3], [4, 5],
];

const objectInArray = [{
  id: 1,
}, {
  id: 2,
}];

const numberInArray = [
  1, 2,
];

// good
const arr = [[0, 1], [2, 3], [4, 5]];

const objectInArray = [
  {
    id: 1,
  },
  {
    id: 2,
  },
];

const numberInArray = [
  1,
  2,
];
```

### 하나의 객체에서 여러 속성에 접근할 때는 객체 비구조화를 사용하자

```jsx
// bad
function getFullName(user) {
  const firstName = user.firstName;
  const lastName = user.lastName;

  return `${firstName} ${lastName}`;
}

// good
function getFullName(user) {
  const { firstName, lastName } = user;
  return `${firstName} ${lastName}`;
}

// best
function getFullName({ firstName, lastName }) {
  return `${firstName} ${lastName}`;
}
```

```jsx
const arr = [1, 2, 3, 4];

// bad
const first = arr[0];
const second = arr[1];

// good
const [first, second] = arr;
```

### 문자열에는 작은 따옴표 ''를 사용하자

```jsx
// bad
const name = "Capt. Janeway";

// bad - template literals should contain interpolation or newlines
const name = `Capt. Janeway`;

// good
const name = 'Capt. Janeway';
```

### 100자가 넘는 문자열을 문자열 연결을 이용해 여러 줄에 걸쳐 쓰지 말자

```jsx
// bad
const errorMessage = 'This is a super long error that was thrown because \
of Batman. When you stop to think about how Batman had anything to do \
with this, you would get nowhere \
fast.';

// bad
const errorMessage = 'This is a super long error that was thrown because ' +
  'of Batman. When you stop to think about how Batman had anything to do ' +
  'with this, you would get nowhere fast.';

// good
const errorMessage = 'This is a super long error that was thrown because of Batman. When you stop to think about how Batman had anything to do with this, you would get nowhere fast.';
```

### 문자열을 생성하는 경우 문자열 연결 대신 템플릿 문자열을 사용하자

```jsx
// bad
function sayHi(name) {
  return 'How are you, ' + name + '?';
}

// bad
function sayHi(name) {
  return ['How are you, ', name, '?'].join();
}

// bad
function sayHi(name) {
  return `How are you, ${ name }?`;
}

// good
function sayHi(name) {
  return `How are you, ${name}?`;
}
```

### 함수선언식 대신 기명 함수표현식을 사용하자

- 함수선언식: 함수명이 정의되어 있고, 별도의 할당 명령이 없는 것
- 함수표현식: 정의한 function을 별도의 변수에 할당하는 것
- 특징:함수 선언식은 호이스팅에 영향을 받지만, 함수 표현식은 호이스팅에 영향을 받지 않는다.
    - 다른 언어에서는 함수를 '특별한 동작을 하는 구조'로 취급하지만, 자바스크립트에서는 함수를 특별한 종류의 값(value)으로 취급한다. 즉, 함수를 다른 변수에 값으로써 '할당'한 것이 곧 함수 표현식이다

```jsx
// bad
function foo() {
  // ...
}

// bad
const foo = function () {
  // ...
};

// good
// 변수 참조 호출과 구분되는 이름
const short = function longUniqueMoreDescriptiveLexicalFoo() {
  // ...
};
```

### 절대 arguments 사용 금지

- 매개변수 이름으로 arguments 사용 금지
    - 함수 스코프에 전해지는 arguments 객체의 참조를 덮어써 버린다.
    - 예)
        
        ```jsx
        // bad
        function foo(name, options, arguments) {
          // ...
        }
        
        // good
        function foo(name, options, args) {
          // ...
        }
        			```
        ```
        
- arguments 대신 나머지 문법(rest syntax) `...` 사용
    - 예)
    
    ```jsx
    // bad
    function concatenateAll() {
      const args = Array.prototype.slice.call(arguments);
      return args.join('');
    }
    
    // good
    function concatenateAll(...args) {
      return args.join('');
    		}
    		```
    
    ```
    

### 함수 시그니처에 공백을 넣자

```jsx
// bad
const f = function(){};
const g = function (){};
const h = function() {};

// good
const x = function () {};
const y = function a() {};
```

### 절대로 매개변수를 바꾸지 말자

```jsx
// bad
function f1(obj) {
  obj.key = 1;
}

// good
function f2(obj) {
  const key = Object.prototype.hasOwnProperty.call(obj, 'key') ? obj.key : 1;
}
```

### 절대로 매개변수를 재할당하지 말자

매개변수를 재할당하는 것은 예측할 수 없는 결과를 불러 일으킨다.

```jsx
// bad
function f1(a) {
  a = 1;
  // ...
}

function f2(a) {
  if (!a) { a = 1; }
  // ...
}

// good
function f3(a) {
  const b = a || 1;
  // ...
}

function f4(a = 1) {
  // ...
}
```

### 가변 인자 함수를 호출할 때는 전개 구문 `...`을 사용하자

```jsx
// bad
const x = [1, 2, 3, 4, 5];
console.log.apply(console, x);

// good
const x = [1, 2, 3, 4, 5];
console.log(...x);

// bad
new (Function.prototype.bind.apply(Date, [null, 2016, 8, 5]));

// good
new Date(...[2016, 8, 5]);
```

### 여러 줄의 시그니처 또는 호출을 취하는 함수는 들여쓰기를 해주자

```jsx
// bad
function foo(bar,
             baz,
             quux) {
  // ...
}

// good
function foo(
  bar,
  baz,
  quux,) {
  // ...
}

// bad
console.log(foo,
  bar,
  baz);

// good
console.log(
  foo,
  bar,
  baz,
);
```

### 익명함수를 사용할 때는 화살표 함수 표현을 사용하자

```jsx
// bad
[1, 2, 3].map(function (x) {
  const y = x + 1;
  return x * y;
});

// good
[1, 2, 3].map((x) => {
  const y = x + 1;
  return x * y;
});
```

### 명확성과 일관성을 위해 항상 인자를 괄호로 감싸자

```jsx
// bad
[1, 2, 3].map(x => x * x);

// good
[1, 2, 3].map((x) => x * x);

// bad
[1, 2, 3].map(number => (
  `A long string with the ${number}. It’s so long that we don’t want it to take up space on the .map line!`));

// good
[1, 2, 3].map((number) => (
  `A long string with the ${number}. It’s so long that we don’t want it to take up space on the .map line!`));

// bad
[1, 2, 3].map(x => {
  const y = x + 1;
  return x * y;
});

// good
[1, 2, 3].map((x) => {
  const y = x + 1;
  return x * y;
});
```

### 중복되는 클래스 멤버를 만들지 말자

중복된 클래스 멤버를 선언하면 암묵적으로 마지막 멤버가 적용된다.

```jsx
// bad
class Foo {
  bar() { return 1; }
  bar() { return 2; }
}

// good
class Foo {
  bar() { return 1; }
}

// good
class Foo {
  bar() { return 2; }
}
```

### 클래스 메소드는 외부 라이브러리나 프레임워크가 구체적으로 비정적 메소드를 요구하지 않는 이상 this를 사용하거나 해당 메소드를 정적 메소드로 만들어야 한다

2주 차 코드 리뷰에서 class 안에서는 this를 사용하거나 static을 붙여줘야 한다는 리뷰를 받았는데, 이게 그 말인 것 같다.

```jsx
// bad
class Foo {
  bar() {
    console.log('bar');
  }
}

// good - this를 사용했습니다
class Foo {
  bar() {
    console.log(this.bar);
  }
}

// good - constructor가 면제됩니다
class Foo {
  constructor() {
    // ...
  }
}

// good - 정적 메소드는 this를 사용하지 않는다고 예상할 수 있습니다
class Foo {
  static bar() {
    console.log('bar');
  }
}
```

### 모듈 사용법

```jsx
// bad
const AirbnbStyleGuide = require('./AirbnbStyleGuide');
module.exports = AirbnbStyleGuide.es6;

// ok
import AirbnbStyleGuide from './AirbnbStyleGuide';
export default AirbnbStyleGuide.es6;

// best
import { es6 } from './AirbnbStyleGuide';
export default es6;
```

### 와일드카드 import는 사용하지 말자

와일드카드란? 파일을 지정할 때, 구체적인 이름 대신에 여러 파일을 동시에 지정할 목적으로 사용하는 특수 기호. `＊' 등

```jsx
// bad
import * as AirbnbStyleGuide from './AirbnbStyleGuide';

// good
import AirbnbStyleGuide from './AirbnbStyleGuide';
```

### 가변 바인딩을 export하지 말자(일반적으로는 상수 참조만 export되어야 함)

```jsx
// bad
let foo = 3;
export { foo };

// good
const foo = 3;
export { foo };
```

### 한가지만 export하는 모듈에서는 이름 붙여진 export보다는 default export를 사용하자

하나만 export하는 파일의 가독성과 유지보수성이 더 좋기 때문

```jsx
// bad
export function foo() {}

// good
export default function foo() {}
```

### 여러 줄에 걸친 import는 여러 줄의 배열이나 객체 리터럴처럼 들여쓰기하자

```jsx
// bad
import {longNameA, longNameB, longNameC, longNameD, longNameE} from 'path';

// good
import {
  longNameA,
  longNameB,
  longNameC,
  longNameD,
  longNameE,
} from 'path';
```

### 자바스크립트 파일 확장자를 명시하지 말자

확장자를 명시하면 import하는 모듈의 세부적 구현을 부적절하게 하드코딩하고, 리팩토링을 막게 됨

```jsx
// bad
import foo from './foo.js';
import bar from './bar.jsx';
import baz from './baz/index.jsx';

// good
import foo from './foo';
import bar from './bar';
import baz from './baz';
```

### 이터레이터 사용 않기. for-in이나 for-of같은 루프 대신 자바스크립트의 고급함수를 사용하자

고급함수는 불변 규칙을 적용한다.

사이드 이펙트에 대해 추측하는 것보다 값을 반환하는 순수 함수를 다루는 것이 더 간단하다.

여기서 나온 forEach와 reduce가 익숙하지 않아 따로 포스팅을 해봤는데, 이 포스팅은 [이곳에서](https://velog.io/@hamham/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-map-forEach-reduce) 확인할 수 있다! (for 대신 자바스크립트 함수를 사용하라는 Airbnb 자바스크립트 컨벤션에 부정적인 의견도 적어두었다.)

```jsx
const numbers = [1, 2, 3, 4, 5];

// bad
let sum = 0;
for (let num of numbers) {
  sum += num;
}
sum === 15;

// good
let sum = 0;
numbers.forEach((num) => {
  sum += num;
});
sum === 15;

// best (use the functional force)
const sum = numbers.reduce((total, num) => total + num, 0);
sum === 15;

// bad
const increasedByOne = [];
for (let i = 0; i < numbers.length; i++) {
  increasedByOne.push(numbers[i] + 1);
}

// good
const increasedByOne = [];
numbers.forEach((num) => {
  increasedByOne.push(num + 1);
});

// best (keeping it functional)
const increasedByOne = numbers.map(num => num + 1);
```

### 속성에 접근할 때는 마침표를 사용하자

```jsx
const luke = {
  jedi: true,
  age: 28,
};

// bad
const isJedi = luke['jedi'];

// good
const isJedi = luke.jedi;
```

### 변수를 사용해 속성에 접근할 때는 대괄호 []를 사용하자

속성에 접근할 때 `.`과 `[]`로 접근하는 방법이 있다는 건 알았지만, 이 둘의 차이점은 처음 알았다.

```jsx
const luke = {
  jedi: true,
  age: 28,
};

function getProp(prop) {
  return luke[prop];
}

const isJedi = getProp('jedi');
```

### 제곱 계산을 할 때는 Math.pow 대신 제곱 연산자 **을 사용하자

```jsx
// bad
const binary = Math.pow(2, 10);

// good
const binary = 2 ** 10;
```

### 하나의 변수 선언/할당에는 하나의 const 또는 let을 사용하자

이렇게 하면 쉽게 새로운 변수를 추가할 수 있고, ,를 ;로 바꿔버리는 것에 대해 걱정할 필요가 없다.

```jsx
// bad
const items = getItems(),
    goSportsTeam = true,
    dragonball = 'z';

// bad
// (위 코드와 비교해 실수를 짚어보세요)
const items = getItems(),
    goSportsTeam = true;
    dragonball = 'z';

// good
const items = getItems();
const goSportsTeam = true;
const dragonball = 'z';
```

### const를 그룹화한 다음에 let을 선언하자

```jsx
// bad
let i, len, dragonball,
    items = getItems(),
    goSportsTeam = true;

// bad
let i;
const items = getItems();
let dragonball;
const goSportsTeam = true;
let len;

// good
const goSportsTeam = true;
const items = getItems();
let dragonball;
let i;
let length;
```

### 변수 할당 체이닝을 하지 말자

```jsx
// bad
(function example() {
  // 자바스크립트는 이것을
  // let a = ( b = ( c = 1 ) );
  // 로 해석합니다.
  // let 키워드는 변수 a에만 적용됩니다.
  // 변수 b와 c는 전역 변수가 됩니다.
  let a = b = c = 1;
}());

console.log(a); // throws ReferenceError
console.log(b); // 1
console.log(c); // 1

// good
(function example() {
  let a = 1;
  let b = a;
  let c = a;
}());

console.log(a); // throws ReferenceError
console.log(b); // throws ReferenceError
console.log(c); // throws ReferenceError

// `const`에도 동일하게 적용됩니다
```

### 단항 증감 연산자(++, --)를 사용하지 말자

이건 코드 리뷰 스터디에서 지적받은 부분이다. ++, -- 대신 += 1, -= 1 등을 사용하자

```jsx
// bad

const array = [1, 2, 3];
let num = 1;
num++;
--num;

let sum = 0;
let truthyCount = 0;
for (let i = 0; i < array.length; i++) {
  let value = array[i];
  sum += value;
  if (value) {
    truthyCount++;
  }
}

// good

const array = [1, 2, 3];
let num = 1;
num += 1;
num -= 1;

const sum = array.reduce((a, b) => a + b, 0);
const truthyCount = array.filter(Boolean).length;
```

### 삼항 연산자를 중첩해서는 안되며, 일반적으로 한줄에 표현해야 한다

최근에 이중 삼항연산자를 접하고 해석하는데 오래걸렸는데, 하나만 쓰기를 권항한다는 것을 새롭게 알게되었다.

```jsx
// bad
const foo = maybe1 > maybe2
  ? "bar"
  : value1 > value2 ? "baz" : null;

// split into 2 separated ternary expressions
const maybeNull = value1 > value2 ? 'baz' : null;

// better
const foo = maybe1 > maybe2
  ? 'bar'
  : maybeNull;

// best
const foo = maybe1 > maybe2 ? 'bar' : maybeNull;
```

### 불필요한 삼항 연산자를 사용하지 말자

```jsx
// bad
const foo = a ? a : b;
const bar = c ? true : false;
const baz = c ? false : true;

// good
const foo = a || b;
const bar = !!c;
const baz = !c;
```

### 연산자를 섞어 사용할 때 해당 연산자들을 괄호로 둘러싸자

- 유일한 예외는 산술 연산자 (+, -, **)이다. 이들의 우선순위는 상식적으로 이해할 수 있기 때문!
- /와 *은 섞일 경우 순서가 모호할 수 있으므로 괄호로 감싸는 것을 추천함

```jsx
// bad
const foo = a && b < 0 || c > 0 || d + 1 === 0;

// bad
const bar = a ** b - 5 % d;

// bad
// (a || b) && c 으로 혼동할 수 있습니다.
if (a || b && c) {
  return d;
}

// good
const foo = (a && b < 0) || c > 0 || (d + 1 === 0);

// good
const bar = (a ** b) - (5 % d);

// good
if (a || (b && c)) {
  return d;
}

// good
const bar = a + b / c * d;
```

### 여러 줄의 if와 else문을 사용할 때는 else를 if 블록의 닫는 중괄호와 같은 줄에 두자

```jsx
// bad
if (test) {
  thing1();
  thing2();
}
else {
  thing3();
}

// good
if (test) {
  thing1();
  thing2();
} else {
  thing3();
}
```

### 만약 if 블록이 항상 return 구문을 실행시킨다면, else 블록은 불필요하다

이또한 코드 리뷰에서 지적받은 부분이다. 불필요한 else문을 사용하지 말자.

```jsx
// bad
function foo() {
  if (x) {
    return x;
  } else {
    return y;
  }
}

// bad
function cats() {
  if (x) {
    return x;
  } else if (y) {
    return y;
  }
}

// bad
function dogs() {
  if (x) {
    return x;
  } else {
    if (y) {
      return y;
    }
  }
}

// good
function foo() {
  if (x) {
    return x;
  }

  return y;
}

// good
function cats() {
  if (x) {
    return x;
  }

  if (y) {
    return y;
  }
}

// good
function dogs(x) {
  if (x) {
    if (z) {
      return y;
    }
  } else {
    return z;
  }
}
```

### 제어문 (if, while 등)이 너무 길거나 최대 길이를 넘긴 경우, 각 조건을 새로운 줄에 두자

```jsx
// bad
if ((foo === 123 || bar === 'abc') && doesItLookGoodWhenItBecomesThatLong() && isThisReallyHappening()) {
  thing1();
}

// bad
if (foo === 123 &&
  bar === 'abc') {
  thing1();
}

// bad
if (foo === 123
  && bar === 'abc') {
  thing1();
}

// bad
if (
  foo === 123 &&
  bar === 'abc'
) {
  thing1();
}

// good
if (
  foo === 123
  && bar === 'abc'
) {
  thing1();
}

// good
if (
  (foo === 123 || bar === 'abc')
  && doesItLookGoodWhenItBecomesThatLong()
  && isThisReallyHappening()
) {
  thing1();
}

// good
if (foo === 123 && bar === 'abc') {
  thing1();
}
```

### 한줄 주석을 쓸 때는 //을 사용하세요. 주석 전에는 빈 행을 넣어주자

주석 전에 빈 행을 넣어주는지 몰랐다!

```jsx
// bad
const active = true;  // is current tab

// good
// is current tab
const active = true;

// bad
function getType() {
  console.log('fetching type...');
  // set the default type to 'no type'
  const type = this.type || 'no type';

  return type;
}

// good
function getType() {
  console.log('fetching type...');

  // set the default type to 'no type'
  const type = this.type || 'no type';

  return type;
}

// also good
function getType() {
  // set the default type to 'no type'
  const type = this.type || 'no type';

  return type;
}
```

### 모든 주석은 공백으로 시작해야 한다

```jsx
// bad
//is current tab
const active = true;

// good
// is current tab
const active = true;

// bad
/**
 *make()는 전달된 태그명을 기반으로
 *새로운 요소를 반환한다.
 */
function make(tag) {

  // ...

  return element;
}

// good
/**
 * make()는 전달된 태그명을 기반으로
 * 새로운 요소를 반환한다.
 */
function make(tag) {

  // ...

  return element;
}
```

### 제를 지적하고 재고를 촉구하는 경우나 문제의 해결책을 제안하는 경우 등에는 주석 앞에 FIXME 나 TODO 를 붙임으로써 다른 개발자의 빠른 이해를 돕는다

### 문제를 지적하는 `// FIXME:`

```jsx
class Calculator extends Abacus {
  constructor() {
    super();

    // FIXME: 전역 변수를 사용해서는 안 됨
    total = 0;
  }
}
```

### 문제의 해결책을 제안하는 `// TODO:`

```jsx
class Calculator extends Abacus {
  constructor() {
    super();

    // TODO: total은 옵션 파라메터로 설정해야함
    this.total = 0;
  }
}
```

### 탭은 공백문자 2개로 설정하자

### 주요 중괄호 앞에는 공백을 1개 넣자

```jsx
// bad
function test(){
  console.log('test');
}

// good
function test() {
  console.log('test');
}

// bad
dog.set('attr',{
  age: '1 year',
  breed: 'Bernese Mountain Dog',
});

// good
dog.set('attr', {
  age: '1 year',
  breed: 'Bernese Mountain Dog',
});
```

### 제어문 (if, while 등)의 소괄호 앞에는 공백을 1개 넣자. 함수선언이나 함수호출시 인자 리스트 앞에는 공백을 넣지 말자.

```jsx
// bad
if(isJedi) {
  fight ();
}

// good
if (isJedi) {
  fight();
}

// bad
function fight () {
  console.log ('Swooosh!');
}

// good
function fight() {
  console.log('Swooosh!');
}
```

### 연산자 사이에 공백을 넣자

```jsx
// bad
const x=y+5;

// good
const x = y + 5;
```

### 구문의 앞과 블록의 뒤에는 빈 행을 두자

```jsx
// bad
if (foo) {
  return bar;
}
return baz;

// good
if (foo) {
  return bar;
}

return baz;

// bad
const obj = {
  foo() {
  },
  bar() {
  },
};
return obj;

// good
const obj = {
  foo() {
  },

  bar() {
  },
};

return obj;

// bad
const arr = [
  function foo() {
  },
  function bar() {
  },
];
return arr;

// good
const arr = [
  function foo() {
  },

  function bar() {
  },
];

return arr;
```

### 블록에 빈 행을 끼워 넣지 말자

```jsx
// bad
function bar() {

  console.log(foo);

}

// bad
if (baz) {

  console.log(qux);
} else {
  console.log(foo);

}

// bad
class Foo {

  constructor(bar) {
    this.bar = bar;
  }
}

// good
function bar() {
  console.log(foo);
}

// good
if (baz) {
  console.log(qux);
} else {
  console.log(foo);
}
```

### 대괄호 안쪽에 공백을 두지 말자

대괄호 안쪽에 공백을 둔 코드를 많이 봤는데, 그렇게 하지 말아야겠다.

```jsx
// bad
const foo = [ 1, 2, 3 ];
console.log(foo[ 0 ]);

// good
const foo = [1, 2, 3];
console.log(foo[0]);
```

### 중괄호 안쪽에 공백을 두자

대괄호와 반대로 중괄호 안에는 공백을 두어야 한다!

```jsx
// bad
const foo = {clark: 'kent'};

// good
const foo = { clark: 'kent' };
```

### 객체 리터럴 속성의 키와 값 사이에는 공백을 넣자

```jsx
// bad
var obj = { foo : 42 };
var obj2 = { foo:42 };

// good
var obj = { foo: 42 };
```

### 파일의 마지막 행에는 빈 행을 두되 파일의 시작에는 빈 행을 두지 말자

```jsx
// bad - 여러 개의 빈 줄
var x = 1;

var y = 2;

// bad - 파일 끝에 2개 이상의 빈 줄
var x = 1;
var y = 2;

// bad - 파일 시작에 1개 이상의 빈 줄

var x = 1;
var y = 2;

// good
var x = 1;
var y = 2;
```

### 맨 앞의 쉼표: 안 된다

```jsx
// bad
const story = [
    once
  , upon
  , aTime
];

// good
const story = [
  once,
  upon,
  aTime,
];

// bad
const hero = {
    firstName: 'Ada'
  , lastName: 'Lovelace'
  , birthYear: 1815
  , superPower: 'computers'
};

// good
const hero = {
  firstName: 'Ada',
  lastName: 'Lovelace',
  birthYear: 1815,
  superPower: 'computers',
};
```

### 끝의 쉼표: 좋다

```jsx
// bad - 마지막에 쉼표가 없는 경우 git diff
const hero = {
     firstName: 'Florence',
-    lastName: 'Nightingale'
+    lastName: 'Nightingale',
+    inventorOf: ['coxcomb chart', 'modern nursing']
};

// good - 마지막에 쉼표가 있는 경우 git diff
const hero = {
     firstName: 'Florence',
     lastName: 'Nightingale',
+    inventorOf: ['coxcomb chart', 'modern nursing'],
};

// good ("나머지" 요소 뒤에 쉼표가 없다는 점에 주의하세요)
function createHero(
  firstName,
  lastName,
  inventorOf,
  ...heroArgs
) {
  // does nothing
}
```

### eslint를 쓰자

구문의 끝을 명시하고, 빠뜨린 세미콜론을 잡도록 linter를 설정하자.

### 형변환을 하는 경우 Number를 사용하고, 문자열을 파싱하는 경우에는 기수를 인자로 넘겨 parseInt를 사용하자

```jsx
const inputValue = '4';

// bad
const val = new Number(inputValue);

// bad
const val = +inputValue;

// bad
const val = inputValue >> 0;

// bad
const val = parseInt(inputValue);

// good
const val = Number(inputValue);

// good
const val = parseInt(inputValue, 10);
```

### 객체, 함수, 인스턴스에는 캐멀케이스(camelCase)를 사용

```jsx
// bad
const OBJEcttsssss = {};
const this_is_my_object = {};
function c() {}

// good
const thisIsMyObject = {};
function thisIsMyFunction() {}
```

### 클래스나 생성자에는 파스칼케이스(PascalCase)를 사용

```jsx
// bad
function user(options) {
  this.name = options.name;
}

const bad = new user({
  name: 'nope',
});

// good
class User {
  constructor(options) {
    this.name = options.name;
  }
}

const good = new User({
  name: 'yup',
});
```

### 파일 이름은 default export의 이름과 일치해야 한다

```jsx
// 파일 1 내용
class CheckBox {
  // ...
}
export default CheckBox;

// 파일 2 내용
export default function fortyTwo() { return 42; }

// 파일 3 내용
export default function insideDirectory() {}

// 다른 파일
// bad
import CheckBox from './checkBox'; // PascalCase import/export, camelCase filename
import FortyTwo from './FortyTwo'; // PascalCase import/filename, camelCase export
import InsideDirectory from './InsideDirectory'; // PascalCase import/filename, camelCase export

// bad
import CheckBox from './check_box'; // PascalCase import/export, snake_case filename
import forty_two from './forty_two'; // snake_case import/filename, camelCase export
import inside_directory from './inside_directory'; // snake_case import, camelCase export
import index from './inside_directory/index'; // requiring the index file explicitly
import insideDirectory from './insideDirectory/index'; // requiring the index file explicitly

// good
import CheckBox from './CheckBox'; // PascalCase export/import/filename
import fortyTwo from './fortyTwo'; // camelCase export/import/filename
import insideDirectory from './insideDirectory'; // camelCase export/import/directory name/implicit "index"
// ^ supports both insideDirectory.js and insideDirectory/index.js
```

### 상수 이름을 대문자로 짓는 것은 해당 상수가 (1) 내보내기 될 때, (2) const 타입일 때 (값이 재할당되지 못할 때), (3) 그 상수와 상수가 중첩된 속성이 절대 변하지 않는다는 것을 신뢰할 수 있을 때만 하자

- 이는 변수가 영원히 변하지 않는다는 것을 확신할 수 없을 때 도움을 주기 위한 추가적인 도구이다. 대문자 변수는 변수와 변수의 속성이 변하지 않는다는 것을 프로그래머에게 알려준다.
- 파일 내 상수 이름을 꼭 대문자로 지을 필요는 없지만 내보내기되는 상수 이름은 대문자로 지어야 한다

```jsx
// bad
const PRIVATE_VARIABLE = 'should not be unnecessarily uppercased within a file';

// bad
export const THING_TO_BE_CHANGED = 'should obviously not be uppercased';

// bad
export let REASSIGNABLE_VARIABLE = 'do not use let with uppercase variables';

// ---

// allowed but does not supply semantic value
export const apiKey = 'SOMEKEY';

// better in most cases
export const API_KEY = 'SOMEKEY';

// ---

// bad - unnecessarily uppercases key while adding no semantic value
export const MAPPING = {
  KEY: 'value'
};

// good
export const MAPPING = {
  key: 'value' // 속성인 key는 대문자가 아님에 유의
};
```

### 이벤트

이벤트로 payload의 값을 넘길 경우 raw값 보다는 해시값을 넘겨주자.

```jsx
// bad
$(this).trigger('listingUpdated', listing.id);

// ...

$(this).on('listingUpdated', (e, listingID) => {
  // do something with listingID
});

// good
$(this).trigger('listingUpdated', { listingID: listing.id });

// ...

$(this).on('listingUpdated', (e, data) => {
  // do something with data.listingID
});
```

### ES6 - isNaN 대신 Number.isNaN을 사용하자

### ES6 - isFinite 대신 Number.isFinite을 사용하자