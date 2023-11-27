## Aufgabe 1: Levenshtein-Distanz

### AUSTAUSCH - AUFBAUSCH 

| -   |     | A   | U   | F   | B   | A   | U   | S   | C   | H   |
|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|
|     | 0   | 1   | 2   | 3   | 4   | 5   | 6   | 7   | 8   | 9   |
| A   | 1   | 0   | 1   | 2   | 3   | 4   | 5   | 6   | 7   | 8   |
| U   | 2   | 1   | 0   | 1   | 2   | 3   | 4   | 5   | 6   | 7   |
| S   | 3   | 2   | 1   | 1   | 2   | 3   | 4   | 4   | 5   | 6   |
| T   | 4   | 3   | 2   | 2   | 2   | 3   | 4   | 5   | 5   | 6   |
| A   | 5   | 4   | 3   | 3   | 3   | 2   | 3   | 4   | 5   | 6   |
| U   | 6   | 5   | 4   | 4   | 4   | 3   | 2   | 3   | 4   | 5   |
| S   | 7   | 6   | 5   | 5   | 5   | 4   | 3   | 2   | 3   | 4   |
| C   | 8   | 7   | 6   | 6   | 6   | 5   | 4   | 3   | 2   | 3   |
| H   | 9   | 8   | 7   | 7   | 7   | 6   | 5   | 4   | 3   | 2   |

### BARBAREN - BARBARA

|     | B   | A   | R   | B   | A   | R   | A   |
|-----|-----|-----|-----|-----|-----|-----|-----|
| B   |     |     |     |     |     |     |     |
| A   |     |     |     |     |     |     |     |
| R   |     |     |     |     |     |     |     |
| B   |     |     |     |     |     |     |     |
| A   |     |     |     |     |     |     |     |
| R   |     |     |     |     |     |     |     |
| E   |     |     |     |     |     |     |     |
| N   |     |     |     |     |     |     |     |

### COCACOLA - COCAINA

|     | C   | O   | C   | A   | I   | N   | A   |
|-----|-----|-----|-----|-----|-----|-----|-----|
| C   |     |     |     |     |     |     |     |
| O   |     |     |     |     |     |     |     |
| C   |     |     |     |     |     |     |     |
| A   |     |     |     |     |     |     |     |
| C   |     |     |     |     |     |     |     |
| O   |     |     |     |     |     |     |     |
| L   |     |     |     |     |     |     |     |
| A   |     |     |     |     |     |     |     |