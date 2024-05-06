import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInkomensvoorzieningsoort } from 'app/shared/model/inkomensvoorzieningsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './inkomensvoorzieningsoort.reducer';

export const InkomensvoorzieningsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inkomensvoorzieningsoortEntity = useAppSelector(state => state.inkomensvoorzieningsoort.entity);
  const loading = useAppSelector(state => state.inkomensvoorzieningsoort.loading);
  const updating = useAppSelector(state => state.inkomensvoorzieningsoort.updating);
  const updateSuccess = useAppSelector(state => state.inkomensvoorzieningsoort.updateSuccess);

  const handleClose = () => {
    navigate('/inkomensvoorzieningsoort');
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
      ...inkomensvoorzieningsoortEntity,
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
          ...inkomensvoorzieningsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inkomensvoorzieningsoort.home.createOrEditLabel" data-cy="InkomensvoorzieningsoortCreateUpdateHeading">
            Create or edit a Inkomensvoorzieningsoort
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
                <ValidatedField name="id" required readOnly id="inkomensvoorzieningsoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Code"
                id="inkomensvoorzieningsoort-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="inkomensvoorzieningsoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="inkomensvoorzieningsoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Regeling" id="inkomensvoorzieningsoort-regeling" name="regeling" data-cy="regeling" type="text" />
              <ValidatedField
                label="Regelingcode"
                id="inkomensvoorzieningsoort-regelingcode"
                name="regelingcode"
                data-cy="regelingcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vergoeding"
                id="inkomensvoorzieningsoort-vergoeding"
                name="vergoeding"
                data-cy="vergoeding"
                type="text"
              />
              <ValidatedField
                label="Vergoedingscode"
                id="inkomensvoorzieningsoort-vergoedingscode"
                name="vergoedingscode"
                data-cy="vergoedingscode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Wet" id="inkomensvoorzieningsoort-wet" name="wet" data-cy="wet" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inkomensvoorzieningsoort" replace color="info">
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

export default InkomensvoorzieningsoortUpdate;
