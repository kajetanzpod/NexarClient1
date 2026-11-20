NEXAR CLIENT — Fabric 1.21.4

To repozytorium zawiera projekt źródłowy i workflow GitHub Actions, który próbuje
zbudować plik JAR po wypchnięciu kodu na GitHuba.

JAK UZYSKAĆ JAR:
1. Zaloguj się na github.com i utwórz nowe repozytorium (np. NexarClient).
2. Wgraj do repozytorium wszystkie pliki i foldery z tego ZIP-a.
3. Otwórz zakładkę Actions w repozytorium.
4. Wybierz workflow "Build Nexar Client" i uruchom go przyciskiem Run workflow,
   albo poczekaj, aż zbuduje się automatycznie po dodaniu plików.
5. Po zakończeniu otwórz ukończone uruchomienie workflow.
6. Na dole w sekcji Artifacts pobierz "NexarClient-Fabric-1.21.4".
7. Rozpakuj pobrane archiwum i skopiuj plik .jar do .minecraft/mods.

Wymagania: Minecraft 1.21.4, Fabric Loader, Fabric API 1.21.4 i Java 21.

UWAGA:
- Nie mogę utworzyć repozytorium na Twoim koncie ani opublikować go pod własnym
  linkiem. ZIP jest przygotowany do wgrania przez Ciebie.
- To wciąż wczesny prototyp. Workflow może wykryć błędy kompilacji wymagające
  poprawek. Nie traktuj pliku jako przetestowanego na Anarchia.gg.
- AutoDrip wykonuje powtarzaną zwykłą interakcję z klapą przy trzymanym pointed
  dripstone. Działanie zależy od mechaniki serwera i może nie powodować
  oczekiwanego efektu. Automatyzację testuj tylko tam, gdzie jest dozwolona.
- Tracers, Nametags i NoPush nie są jeszcze zaimplementowane.
