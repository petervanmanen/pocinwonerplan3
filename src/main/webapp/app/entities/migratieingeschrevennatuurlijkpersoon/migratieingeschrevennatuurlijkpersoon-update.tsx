import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMigratieingeschrevennatuurlijkpersoon } from 'app/shared/model/migratieingeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './migratieingeschrevennatuurlijkpersoon.reducer';

export const MigratieingeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const migratieingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/migratieingeschrevennatuurlijkpersoon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...migratieingeschrevennatuurlijkpersoonEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...migratieingeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.migratieingeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="MigratieingeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Migratieingeschrevennatuurlijkpersoon
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="migratieingeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Aangevermigratie"
                id="migratieingeschrevennatuurlijkpersoon-aangevermigratie"
                name="aangevermigratie"
                data-cy="aangevermigratie"
                type="text"
              />
              <ValidatedField
                label="Redenwijzigingmigratie"
                id="migratieingeschrevennatuurlijkpersoon-redenwijzigingmigratie"
                name="redenwijzigingmigratie"
                data-cy="redenwijzigingmigratie"
                type="text"
              />
              <ValidatedField
                label="Soortmigratie"
                id="migratieingeschrevennatuurlijkpersoon-soortmigratie"
                name="soortmigratie"
                data-cy="soortmigratie"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/migratieingeschrevennatuurlijkpersoon"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MigratieingeschrevennatuurlijkpersoonUpdate;
