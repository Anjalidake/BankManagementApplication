## 🔄 Project Evolution (Monolith → Microservices)

This project was initially developed as a **monolithic Spring Boot application** and later refactored to demonstrate a **real-world migration path to microservices**.

### Step 1: Preserve Monolith as Version 1
The original monolithic application was preserved and moved into a dedicated module to keep the working version intact.

```bash
git add .
git commit -m "Refactor: move monolith code into monolith module"
git push
