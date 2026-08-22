You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-agent/codex-app-agent/AGENTS.md
Use this screen spec: /home/codex-agent/codex-app-agent/screens-service.md
Project directory: /tmp/resolve-strategy

Task metadata:
- Asana task gid: 1217320883455050
- Asana task name: GB GW4 C1427
- Asana URL: https://app.asana.com/1/1208304498069546/project/1213586227413017/task/1217320883455050
- App name: Resolve Strategy
- Company: RESOLVE PROJECT DELIVERY LTD
- Domain: http://resolveproject.courses/
- Package: resolve.projectdelivery.resolvestrategy
- Prefix: TGRGK
- Type: service
- Description: Специфика компании — консультирование по вопросам управления, разработка стратегий развития бизнеса и оптимизация организационных структур.  
Приложение по предложению услуг компании содержит:  
Каталог услуг и решений: список направлений управленческого консалтинга с сортировкой по категориям, например «Стратегическое планирование», «Управление персоналом», «Оптимизация бизнес-процессов».  
Портфолио (Галерея): демонстрация успешно реализованных проектов по трансформации бизнеса, внедрению систем управления и достигнутых ключевых показателей клиентов.  
База знаний: страница с экспертными статьями по лидерству, управлению изменениями и повышению операционной эффективности (минимум 3 статьи).  
Логика взаимодействия:  
Бронирование консультации: страница записи на стратегическую сессию, организационный аудит или аудит бизнес-процессов с формой, открываемой со страницы услуги по кнопке «Забронировать консультацию».  
Подтверждение: после подтверждения бронирования пользователь видит баннер с номером и деталями сессии, а также уведомление о том, что консультант будет ожидать его в онлайн-конференции или по адресу офиса в назначенное время.  
Настройки приложения содержат:  
Название компании.  
Версию приложения.  
Раздел Customer Support со ссылкой на сайт компании.

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Do not create project-local agent instruction files inside /tmp/resolve-strategy.
3. Implement all required screens/content/data/assets/icon according to the orchestrator AGENTS.md and the screen spec.
4. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
5. Do not push to GitHub, do not update Asana, and do not send Slack.
6. You may run local checks while implementing, but the runner will run quality/build afterward.
7. Keep every Kotlin file conventionally formatted: one statement per line, annotations above declarations, expanded indented Compose blocks, no semicolon-compressed code, and no source line longer than 200 characters.
