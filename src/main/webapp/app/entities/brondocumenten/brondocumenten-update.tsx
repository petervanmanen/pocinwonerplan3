import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBrondocumenten } from 'app/shared/model/brondocumenten.model';
import { getEntity, updateEntity, createEntity, reset } from './brondocumenten.reducer';

export const BrondocumentenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const brondocumentenEntity = useAppSelector(state => state.brondocumenten.entity);
  const loading = useAppSelector(state => state.brondocumenten.loading);
  const updating = useAppSelector(state => state.brondocumenten.updating);
  const updateSuccess = useAppSelector(state => state.brondocumenten.updateSuccess);

  const handleClose = () => {
    navigate('/brondocumenten');
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
      ...brondocumentenEntity,
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
          ...brondocumentenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.brondocumenten.home.createOrEditLabel" data-cy="BrondocumentenCreateUpdateHeading">
            Create or edit a Brondocumenten
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
                <ValidatedField name="id" required readOnly id="brondocumenten-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aktegemeente"
                id="brondocumenten-aktegemeente"
                name="aktegemeente"
                data-cy="aktegemeente"
                type="text"
              />
              <ValidatedField
                label="Datumdocument"
                id="brondocumenten-datumdocument"
                name="datumdocument"
                data-cy="datumdocument"
                type="text"
              />
              <ValidatedField
                label="Documentgemeente"
                id="brondocumenten-documentgemeente"
                name="documentgemeente"
                data-cy="documentgemeente"
                type="text"
              />
              <ValidatedField
                label="Documentidentificatie"
                id="brondocumenten-documentidentificatie"
                name="documentidentificatie"
                data-cy="documentidentificatie"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Documentomschrijving"
                id="brondocumenten-documentomschrijving"
                name="documentomschrijving"
                data-cy="documentomschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/brondocumenten" replace color="info">
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

export default BrondocumentenUpdate;
