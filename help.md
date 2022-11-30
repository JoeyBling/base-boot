# NOTE

# 版本记录

- [发行说明](./CHANGELOG.md)

> 打包命令：`mvn clean install -U -P master`

## Git说明

`.gitignore`只能忽略那些原来没有被追踪的文件，如果某些文件已经被纳入了版本管理中， 则修改`.gitignore`是无效的

> `Git`清除本地缓存命令

```bash
git rm -r --cached .
git add .
git commit -m 'update .gitignore'
```
