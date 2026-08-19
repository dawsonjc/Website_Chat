# Focused Cassandra CRM schema

`project.cql` continues to own users, roles, conversations, messages, and events. `crm-schema.cql` adds only organization/company/account, contact, lead, deal/opportunity, pipeline, pipeline stage, task, note, activity, and team data. Docker Compose applies the two schema files in that order. `crm-query-examples.cql` is documentation only.

`workspace_id` is the CRM tenant boundary. User references point to `user_table.userid`; global roles remain in `roles` and `user_role`, while `team_role` applies only inside a team.

Complete records live in `*_by_id`. The other tables are query projections for CRM screens. The service must write the canonical record and its projections. If an owner, status, stage, organization, contact, email, or other primary-key field changes, delete the old projection row and insert its replacement.

Normalize names, emails, and domains in application code. Use prepared statements and driver paging state, store `due_month` as `YYYY-MM`, calculate `activity_day` in the workspace timezone, and avoid `ALLOW FILTERING`. Cassandra is not suitable for fuzzy full-text search or arbitrary analytics; add purpose-built search or reporting storage if needed.
