-- Focused CRM schema for Cassandra 4.1.
-- project.cql already owns users, roles, conversations, messages, and events.
USE mykeyspacename;

CREATE TYPE IF NOT EXISTS crm_address
    (
        line1        text,
        line2        text,
        city         text,
        region       text,
        postal_code  text,
        country_code text
    );

-- Organization / company / account
CREATE TABLE IF NOT EXISTS crm_organizations_by_id
(
    workspace_id           uuid,
    organization_id        uuid,
    name                   text,
    normalized_name        text,
    organization_type      text,
    industry               text,
    website                text,
    email_domain           text,
    phone                  text,
    owner_user_id          uuid,
    parent_organization_id uuid,
    employee_count         int,
    annual_revenue         decimal,
    currency               text,
    billing_address        frozen<crm_address>,
    shipping_address       frozen<crm_address>,
    description            text,
    status                 text,
    custom_fields          map<text,text>,
    created_at             timestamp,
    updated_at             timestamp,
    created_by_user_id     uuid,
    updated_by_user_id     uuid,
    PRIMARY KEY ((workspace_id, organization_id))
);
CREATE TABLE IF NOT EXISTS crm_organizations_by_owner
(
    workspace_id      uuid,
    owner_user_id     uuid,
    status            text,
    updated_at        timestamp,
    organization_id   uuid,
    name              text,
    organization_type text,
    industry          text,
    phone             text,
    PRIMARY KEY ((workspace_id, owner_user_id, status), updated_at, organization_id)
)
            WITH CLUSTERING ORDER BY (updated_at DESC, organization_id ASC);
CREATE TABLE IF NOT EXISTS crm_organization_ids_by_name
(
    workspace_id    uuid,
    normalized_name text,
    organization_id uuid,
    name            text,
    status          text,
    PRIMARY KEY ((workspace_id, normalized_name), organization_id)
);
CREATE TABLE IF NOT EXISTS crm_organization_ids_by_domain
(
    workspace_id    uuid,
    email_domain    text,
    organization_id uuid,
    name            text,
    status          text,
    PRIMARY KEY ((workspace_id, email_domain), organization_id)
);

-- Contact
CREATE TABLE IF NOT EXISTS crm_contacts_by_id
(
    workspace_id       uuid,
    contact_id         uuid,
    organization_id    uuid,
    first_name         text,
    last_name          text,
    full_name          text,
    normalized_name    text,
    email              text,
    normalized_email   text,
    phone              text,
    mobile_phone       text,
    job_title          text,
    department         text,
    owner_user_id      uuid,
    lifecycle_stage    text,
    preferred_channel  text,
    mailing_address    frozen<crm_address>,
    do_not_email       boolean,
    do_not_call        boolean,
    status             text,
    custom_fields      map<text,text>,
    last_contacted_at  timestamp,
    created_at         timestamp,
    updated_at         timestamp,
    created_by_user_id uuid,
    updated_by_user_id uuid,
    PRIMARY KEY ((workspace_id, contact_id))
);
CREATE TABLE IF NOT EXISTS crm_contacts_by_organization
(
    workspace_id    uuid,
    organization_id uuid,
    status          text,
    normalized_name text,
    contact_id      uuid,
    full_name       text,
    email           text,
    phone           text,
    job_title       text,
    owner_user_id   uuid,
    updated_at      timestamp,
    PRIMARY KEY ((workspace_id, organization_id, status), normalized_name, contact_id)
);
CREATE TABLE IF NOT EXISTS crm_contacts_by_owner
(
    workspace_id    uuid,
    owner_user_id   uuid,
    status          text,
    updated_at      timestamp,
    contact_id      uuid,
    organization_id uuid,
    full_name       text,
    email           text,
    phone           text,
    lifecycle_stage text,
    PRIMARY KEY ((workspace_id, owner_user_id, status), updated_at, contact_id)
)
            WITH CLUSTERING ORDER BY (updated_at DESC, contact_id ASC);
CREATE TABLE IF NOT EXISTS crm_contact_ids_by_email
(
    workspace_id     uuid,
    normalized_email text,
    contact_id       uuid,
    organization_id  uuid,
    full_name        text,
    status           text,
    PRIMARY KEY ((workspace_id, normalized_email), contact_id)
);

-- Lead
CREATE TABLE IF NOT EXISTS crm_leads_by_id
(
    workspace_id              uuid,
    lead_id                   uuid,
    first_name                text,
    last_name                 text,
    full_name                 text,
    company_name              text,
    email                     text,
    normalized_email          text,
    phone                     text,
    website                   text,
    job_title                 text,
    owner_user_id             uuid,
    lead_source               text,
    status                    text,
    rating                    text,
    score                     int,
    estimated_value           decimal,
    currency                  text,
    address                   frozen<crm_address>,
    description               text,
    custom_fields             map<text,text>,
    converted_organization_id uuid,
    converted_contact_id      uuid,
    converted_deal_id         uuid,
    converted_at              timestamp,
    created_at                timestamp,
    updated_at                timestamp,
    created_by_user_id        uuid,
    updated_by_user_id        uuid,
    PRIMARY KEY ((workspace_id, lead_id))
);
CREATE TABLE IF NOT EXISTS crm_leads_by_owner_status
(
    workspace_id    uuid,
    owner_user_id   uuid,
    status          text,
    updated_at      timestamp,
    lead_id         uuid,
    full_name       text,
    company_name    text,
    email           text,
    rating          text,
    score           int,
    estimated_value decimal,
    currency        text,
    PRIMARY KEY ((workspace_id, owner_user_id, status), updated_at, lead_id)
)
            WITH CLUSTERING ORDER BY (updated_at DESC, lead_id ASC);
CREATE TABLE IF NOT EXISTS crm_lead_ids_by_email
(
    workspace_id     uuid,
    normalized_email text,
    lead_id          uuid,
    full_name        text,
    company_name     text,
    status           text,
    PRIMARY KEY ((workspace_id, normalized_email), lead_id)
);

-- Pipeline and pipeline stage
CREATE TABLE IF NOT EXISTS crm_pipelines_by_id
(
    workspace_id uuid,
    pipeline_id  uuid,
    name         text,
    description  text,
    is_default   boolean,
    status       text,
    created_at   timestamp,
    updated_at   timestamp,
    PRIMARY KEY ((workspace_id, pipeline_id))
);
CREATE TABLE IF NOT EXISTS crm_pipelines_by_workspace
(
    workspace_id uuid,
    status       text,
    name         text,
    pipeline_id  uuid,
    description  text,
    is_default   boolean,
    updated_at   timestamp,
    PRIMARY KEY ((workspace_id, status), name, pipeline_id)
);
CREATE TABLE IF NOT EXISTS crm_pipeline_stages_by_pipeline
(
    workspace_id  uuid,
    pipeline_id   uuid,
    display_order int,
    stage_id      uuid,
    name          text,
    probability   int,
    stage_type    text,
    is_closed     boolean,
    is_won        boolean,
    created_at    timestamp,
    updated_at    timestamp,
    PRIMARY KEY ((workspace_id, pipeline_id), display_order, stage_id)
);

-- Deal / opportunity
CREATE TABLE IF NOT EXISTS crm_deals_by_id
(
    workspace_id        uuid,
    deal_id             uuid,
    name                text,
    organization_id     uuid,
    primary_contact_id  uuid,
    owner_user_id       uuid,
    pipeline_id         uuid,
    stage_id            uuid,
    stage_name          text,
    amount              decimal,
    currency            text,
    probability         int,
    expected_revenue    decimal,
    expected_close_date date,
    deal_type           text,
    lead_source         text,
    next_step           text,
    loss_reason         text,
    status              text,
    description         text,
    custom_fields       map<text,text>,
    created_at          timestamp,
    updated_at          timestamp,
    closed_at           timestamp,
    created_by_user_id  uuid,
    updated_by_user_id  uuid,
    PRIMARY KEY ((workspace_id, deal_id))
);
CREATE TABLE IF NOT EXISTS crm_deals_by_pipeline_stage
(
    workspace_id        uuid,
    pipeline_id         uuid,
    stage_id            uuid,
    expected_close_date date,
    deal_id             uuid,
    name                text,
    organization_id     uuid,
    owner_user_id       uuid,
    amount              decimal,
    currency            text,
    probability         int,
    updated_at          timestamp,
    PRIMARY KEY ((workspace_id, pipeline_id, stage_id), expected_close_date, deal_id)
)
            WITH CLUSTERING ORDER BY (expected_close_date ASC, deal_id ASC);
CREATE TABLE IF NOT EXISTS crm_deals_by_owner_status
(
    workspace_id        uuid,
    owner_user_id       uuid,
    status              text,
    expected_close_date date,
    deal_id             uuid,
    name                text,
    organization_id     uuid,
    pipeline_id         uuid,
    stage_id            uuid,
    stage_name          text,
    amount              decimal,
    currency            text,
    probability         int,
    PRIMARY KEY ((workspace_id, owner_user_id, status), expected_close_date, deal_id)
)
            WITH CLUSTERING ORDER BY (expected_close_date ASC, deal_id ASC);
CREATE TABLE IF NOT EXISTS crm_deals_by_organization
(
    workspace_id        uuid,
    organization_id     uuid,
    status              text,
    updated_at          timestamp,
    deal_id             uuid,
    name                text,
    owner_user_id       uuid,
    stage_name          text,
    amount              decimal,
    currency            text,
    expected_close_date date,
    PRIMARY KEY ((workspace_id, organization_id, status), updated_at, deal_id)
)
            WITH CLUSTERING ORDER BY (updated_at DESC, deal_id ASC);
CREATE TABLE IF NOT EXISTS crm_deals_by_contact
(
    workspace_id    uuid,
    contact_id      uuid,
    status          text,
    updated_at      timestamp,
    deal_id         uuid,
    name            text,
    organization_id uuid,
    stage_name      text,
    amount          decimal,
    currency        text,
    PRIMARY KEY ((workspace_id, contact_id, status), updated_at, deal_id)
)
            WITH CLUSTERING ORDER BY (updated_at DESC, deal_id ASC);

-- Task
CREATE TABLE IF NOT EXISTS crm_tasks_by_id
(
    workspace_id        uuid,
    task_id             uuid,
    subject             text,
    description         text,
    owner_user_id       uuid,
    assigned_by_user_id uuid,
    related_entity_type text,
    related_entity_id   uuid,
    priority            text,
    status              text,
    due_at              timestamp,
    reminder_at         timestamp,
    completed_at        timestamp,
    created_at          timestamp,
    updated_at          timestamp,
    PRIMARY KEY ((workspace_id, task_id))
);
CREATE TABLE IF NOT EXISTS crm_tasks_by_owner_due_month
(
    workspace_id        uuid,
    owner_user_id       uuid,
    status              text,
    due_month           text,
    due_at              timestamp,
    task_id             uuid,
    subject             text,
    priority            text,
    related_entity_type text,
    related_entity_id   uuid,
    PRIMARY KEY ((workspace_id, owner_user_id, status, due_month), due_at, task_id)
)
            WITH CLUSTERING ORDER BY (due_at ASC, task_id ASC);
CREATE TABLE IF NOT EXISTS crm_tasks_by_entity
(
    workspace_id  uuid,
    entity_type   text,
    entity_id     uuid,
    status        text,
    due_at        timestamp,
    task_id       uuid,
    subject       text,
    owner_user_id uuid,
    priority      text,
    PRIMARY KEY ((workspace_id, entity_type, entity_id, status), due_at, task_id)
)
            WITH CLUSTERING ORDER BY (due_at ASC, task_id ASC);

-- Note
CREATE TABLE IF NOT EXISTS crm_notes_by_id
(
    workspace_id   uuid,
    note_id        uuid,
    entity_type    text,
    entity_id      uuid,
    author_user_id uuid,
    body           text,
    is_pinned      boolean,
    created_at     timestamp,
    updated_at     timestamp,
    PRIMARY KEY ((workspace_id, note_id))
);
CREATE TABLE IF NOT EXISTS crm_notes_by_entity
(
    workspace_id   uuid,
    entity_type    text,
    entity_id      uuid,
    created_time   timeuuid,
    note_id        uuid,
    author_user_id uuid,
    body           text,
    is_pinned      boolean,
    created_at     timestamp,
    updated_at     timestamp,
    PRIMARY KEY ((workspace_id, entity_type, entity_id), created_time, note_id)
)
            WITH CLUSTERING ORDER BY (created_time DESC, note_id ASC);

-- Activity
CREATE TABLE IF NOT EXISTS crm_activities_by_id
(
    workspace_id        uuid,
    activity_id         uuid,
    activity_type       text,
    subject             text,
    body                text,
    actor_user_id       uuid,
    owner_user_id       uuid,
    related_entity_type text,
    related_entity_id   uuid,
    direction           text,
    outcome             text,
    duration_seconds    int,
    occurred_at         timestamp,
    metadata            map<text,text>,
    created_at          timestamp,
    PRIMARY KEY ((workspace_id, activity_id))
);
CREATE TABLE IF NOT EXISTS crm_activities_by_entity
(
    workspace_id     uuid,
    entity_type      text,
    entity_id        uuid,
    occurred_time    timeuuid,
    activity_id      uuid,
    activity_type    text,
    subject          text,
    actor_user_id    uuid,
    owner_user_id    uuid,
    direction        text,
    outcome          text,
    duration_seconds int,
    occurred_at      timestamp,
    metadata         map<text,text>,
    PRIMARY KEY ((workspace_id, entity_type, entity_id), occurred_time, activity_id)
)
    WITH CLUSTERING ORDER BY (occurred_time DESC, activity_id ASC)
     AND compaction = {'class':'TimeWindowCompactionStrategy'};
CREATE TABLE IF NOT EXISTS crm_activities_by_owner_day
(
    workspace_id  uuid,
    owner_user_id uuid,
    activity_day  date,
    occurred_time timeuuid,
    activity_id   uuid,
    activity_type text,
    subject       text,
    entity_type   text,
    entity_id     uuid,
    direction     text,
    outcome       text,
    occurred_at   timestamp,
    PRIMARY KEY ((workspace_id, owner_user_id, activity_day), occurred_time, activity_id)
)
    WITH CLUSTERING ORDER BY (occurred_time DESC, activity_id ASC)
     AND compaction = {'class':'TimeWindowCompactionStrategy'};

-- Team. user_id values reference project.cql user_table.userid.
CREATE TABLE IF NOT EXISTS crm_teams_by_id
(
    workspace_id    uuid,
    team_id         uuid,
    name            text,
    description     text,
    manager_user_id uuid,
    status          text,
    created_at      timestamp,
    updated_at      timestamp,
    PRIMARY KEY ((workspace_id, team_id))
);
CREATE TABLE IF NOT EXISTS crm_teams_by_workspace
(
    workspace_id    uuid,
    status          text,
    name            text,
    team_id         uuid,
    manager_user_id uuid,
    description     text,
    PRIMARY KEY ((workspace_id, status), name, team_id)
);
CREATE TABLE IF NOT EXISTS crm_team_members_by_team
(
    workspace_id uuid,
    team_id      uuid,
    user_id      uuid,
    team_role    text,
    joined_at    timestamp,
    status       text,
    PRIMARY KEY ((workspace_id, team_id), user_id)
);
CREATE TABLE IF NOT EXISTS crm_teams_by_user
(
    workspace_id uuid,
    user_id      uuid,
    team_id      uuid,
    team_name    text,
    team_role    text,
    status       text,
    joined_at    timestamp,
    PRIMARY KEY ((workspace_id, user_id), team_id)
);
