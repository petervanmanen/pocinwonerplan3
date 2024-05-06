import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICultuurcodebebouwd } from 'app/shared/model/cultuurcodebebouwd.model';
import { getEntity, updateEntity, createEntity, reset } from './cultuurcodebebouwd.reducer';

export const CultuurcodebebouwdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cultuurcodebebouwdEntity = useAppSelector(state => state.cultuurcodebebouwd.entity);
  const loading = useAppSelector(state => state.cultuurcodebebouwd.loading);
  const updating = useAppSelector(state => state.cultuurcodebebouwd.updating);
  const updateSuccess = useAppSelector(state => state.cultuurcodebebouwd.updateSuccess);

  const handleClose = () => {
    navigate('/cultuurcodebebouwd');
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
      ...cultuurcodebebouwdEntity,
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
          ...cultuurcodebebouwdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.cultuurcodebebouwd.home.createOrEditLabel" data-cy="CultuurcodebebouwdCreateUpdateHeading">
            Create or edit a Cultuurcodebebouwd
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
                <ValidatedField name="id" required readOnly id="cultuurcodebebouwd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Cultuurcodebebouwd"
                id="cultuurcodebebouwd-cultuurcodebebouwd"
                name="cultuurcodebebouwd"
                data-cy="cultuurcodebebouwd"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidcultuurcodebebouwd"
                id="cultuurcodebebouwd-datumbegingeldigheidcultuurcodebebouwd"
                name="datumbegingeldigheidcultuurcodebebouwd"
                data-cy="datumbegingeldigheidcultuurcodebebouwd"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidcultuurcodebebouwd"
                id="cultuurcodebebouwd-datumeindegeldigheidcultuurcodebebouwd"
                name="datumeindegeldigheidcultuurcodebebouwd"
                data-cy="datumeindegeldigheidcultuurcodebebouwd"
                type="date"
              />
              <ValidatedField
                label="Naamcultuurcodebebouwd"
                id="cultuurcodebebouwd-naamcultuurcodebebouwd"
                name="naamcultuurcodebebouwd"
                data-cy="naamcultuurcodebebouwd"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cultuurcodebebouwd" replace color="info">
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

export default CultuurcodebebouwdUpdate;
