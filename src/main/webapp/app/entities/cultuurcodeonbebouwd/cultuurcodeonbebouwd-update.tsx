import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICultuurcodeonbebouwd } from 'app/shared/model/cultuurcodeonbebouwd.model';
import { getEntity, updateEntity, createEntity, reset } from './cultuurcodeonbebouwd.reducer';

export const CultuurcodeonbebouwdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cultuurcodeonbebouwdEntity = useAppSelector(state => state.cultuurcodeonbebouwd.entity);
  const loading = useAppSelector(state => state.cultuurcodeonbebouwd.loading);
  const updating = useAppSelector(state => state.cultuurcodeonbebouwd.updating);
  const updateSuccess = useAppSelector(state => state.cultuurcodeonbebouwd.updateSuccess);

  const handleClose = () => {
    navigate('/cultuurcodeonbebouwd');
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
      ...cultuurcodeonbebouwdEntity,
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
          ...cultuurcodeonbebouwdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.cultuurcodeonbebouwd.home.createOrEditLabel" data-cy="CultuurcodeonbebouwdCreateUpdateHeading">
            Create or edit a Cultuurcodeonbebouwd
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
                <ValidatedField name="id" required readOnly id="cultuurcodeonbebouwd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Cultuurcodeonbebouwd"
                id="cultuurcodeonbebouwd-cultuurcodeonbebouwd"
                name="cultuurcodeonbebouwd"
                data-cy="cultuurcodeonbebouwd"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidcultuurcodeonbebouwd"
                id="cultuurcodeonbebouwd-datumbegingeldigheidcultuurcodeonbebouwd"
                name="datumbegingeldigheidcultuurcodeonbebouwd"
                data-cy="datumbegingeldigheidcultuurcodeonbebouwd"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidcultuurcodeonbebouwd"
                id="cultuurcodeonbebouwd-datumeindegeldigheidcultuurcodeonbebouwd"
                name="datumeindegeldigheidcultuurcodeonbebouwd"
                data-cy="datumeindegeldigheidcultuurcodeonbebouwd"
                type="date"
              />
              <ValidatedField
                label="Naamcultuurcodeonbebouwd"
                id="cultuurcodeonbebouwd-naamcultuurcodeonbebouwd"
                name="naamcultuurcodeonbebouwd"
                data-cy="naamcultuurcodeonbebouwd"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cultuurcodeonbebouwd" replace color="info">
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

export default CultuurcodeonbebouwdUpdate;
