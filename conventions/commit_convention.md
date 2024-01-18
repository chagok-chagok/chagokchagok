# 커밋 컨벤션

---

### 구조

`Type: 제목` +`Body` + `footer`

```
Feat: 회원가입 기능 구현

SMS, 이메일 중복확인 API 개발

Resolves: #123
Ref: #456
Related to: #48, #45
```

### Type

※ 태그는 영어로 쓰되 첫 문자는 대문자, `:` 뒤에만 Space 들어감(앞엔X)

※ 깃모지가 헷갈릴땐 : [https://gitmoji.dev/](https://gitmoji.dev/) (플러그인 사용해야함)

| 아이콘 | 코드 | 설명 |
| --- | --- | --- |
| 🎉 | :tada: | 프로젝트 시작 |
| ✨ | :sparkles: | 새 기능 |
| 🎨 | :art: | 코드의 구조/형태 개선 |
| 🔥 | :fire: | 코드/파일 삭제 |
| 🐛 | :bug: | 버그 수정 |
| 🚑 | :ambulance: | 긴급 수정 |
| ♻️ | :recycle: | 코드 리팩토링 |
| ⚡️ | :zap: | 성능 개선 |
| 📝 | :memo: | 문서 추가/수정 |
| 💄 | :lipstick: | UI/스타일 파일 추가/수정 |
| 💡 | :bulb: | 주석 추가/수정 |
| ✅ | :white_check_mark: | 테스트 추가/수정 |
| 🔧 | :wrench: | 구성 파일 추가/삭제 |
| ➕ | :heavy_plus_sign: | 의존성 추가 |
| ➖ | :heavy_minus_sign: | 의존성 제거 |
| 🔒 | :lock: | 보안 이슈 수정 |
| 🔀 | :twisted_rightwards_arrows: | 브랜치 합병 |
| ⏪ | :rewind: | 변경 내용 되돌리기 |
| 🗃 | :card_file_box | 데이터베이스 관련 수정 |
| 🙈 | :see_no_evil: | .gitignore 추가/수정 |
| 💚 | :green_heart: | CI 빌드 수정 |
| 📌 | :pushpin: | 특정 버전 의존성 고정 |
| 👷 | :construction_worker: | CI 빌드 시스템 추가/수정 |
| 🔨 | :hammer: | 개발 스크립트 추가/수정 |
| 💩 | :poop: | 똥싼 코드 |
| 📦 | :package: | 컴파일된 파일 추가/수정 |
| 🚚 | :truck: | 리소스 이동, 이름 변경 |

- 깃모지 없이 텍스트 태그 사용시
    
    
    | Feat | 새로운 기능 추가 |
    | --- | --- |
    | Fix | 버그 수정 |
    | Docs | 문서 수정 |
    | Design | Css등 사용자 UI변경 |
    | Style | 코드 구조, 형태 개선 |
    | Refactor | 코드 리팩토링 |
    | Test | 테스트코드, 리팩토링코드 추가 |
    | Chore | 빌드 업무 수정, 패키지 매니저 수정 |
    | Init | 프로젝트 초기생성 |
    | Rename | 파일 혹은 폴더명 수정 |
    | Move | 파일 이동 |
    | Remove | 파일 삭제 |
    | Comment | 주석 변경 |
    | !BREAKING CHANGE | 커다란 API 변경 |
    | !HOTFIX | 급하게 치명적 버그 고침 |
    |  | 변경내용 되돌리기 |

### 제목

※ 최대 50글자가 넘지 않도록하고 마침표 및 특수기호 사용X, 요점만적기(서술X)

### Body

- 본문은 한줄당 72자 이내
- 본문내용은 양에 구애받지않고 상세히 작성
- 본문내용은 어떻게 변경했는지보다 무엇을 변경했는지 또는 왜 변경했는지를 설명

### Footer

- 필수 아님! 필요할때 쓰기
- `유형: #이슈번호` 형식으로 사용
- 여러개 이슈 적을땐 쉼표로 구분
- 이슈트래커 유형은 다음중 하나를 사용
    - Fixes : 이슈 수정중(아직 해결X)
    - Resolves : 이슈를 해결했을때 사용
    - Ref : 참고할 이슈가 있을때 사용
    - Related to : 해당 커밋에 관련된 이슈번호(아직 해결되지 않은 경우)

(EX) Fixes: #45 Related to: #34, #23