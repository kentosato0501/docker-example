# docker-example

Docker 開発演習用プロジェクト

## セットアップ手順

1. Docker Desktop をインストール
2. このリポジトリをクローン
3. `docker compose up -d --build`
4. ブラウザで http://localhost:28080/example/hello.html にアクセス

## ポート番号

| サービス | ホスト側 | コンテナ内 |
|---------|---------|----------|
| Tomcat  | 28080   | 8080     |
| MySQL   | 23306   | 3306     |

## Eclipse での開発

- ソースフォルダー: `src/`
- ビルド出力先: `webapp/app/WEB-INF/classes/`
- volume マウントにより、Eclipse での変更が Docker Tomcat に即時反映されます

## DB 接続情報

- ホスト: `db` (Docker内) / `localhost:23306` (ホストPC / A5:SQL等)
- データベース: `user`
- ユーザー: `user` / パスワード: `Useruseruser1#!`

## samples/ ディレクトリ

演習課題の解答例が格納されています。
- `SelectSample_styled.jsp` - 演習5-1（デザイン変更）の解答例
- `src/SearchServlet.java` - 演習6-1（検索機能）の解答例
  - 使用時は src/ にコピーしてください
