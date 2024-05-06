import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcomponentsoort } from 'app/shared/model/ecomponentsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './ecomponentsoort.reducer';

export const EcomponentsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ecomponentsoortEntity = useAppSelector(state => state.ecomponentsoort.entity);
  const loading = useAppSelector(state => state.ecomponentsoort.loading);
  const updating = useAppSelector(state => state.ecomponentsoort.updating);
  const updateSuccess = useAppSelector(state => state.ecomponentsoort.updateSuccess);

  const handleClose = () => {
    navigate('/ecomponentsoort');
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
      ...ecomponentsoortEntity,
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
          ...ecomponentsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ecomponentsoort.home.createOrEditLabel" data-cy="EcomponentsoortCreateUpdateHeading">
            Create or edit a Ecomponentsoort
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
                <ValidatedField name="id" required readOnly id="ecomponentsoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Ecomponent" id="ecomponentsoort-ecomponent" name="ecomponent" data-cy="ecomponent" type="text" />
              <ValidatedField
                label="Ecomponentcode"
                id="ecomponentsoort-ecomponentcode"
                name="ecomponentcode"
                data-cy="ecomponentcode"
                type="text"
              />
              <ValidatedField label="Kolom" id="ecomponentsoort-kolom" name="kolom" data-cy="kolom" type="text" />
              <ValidatedField label="Kolomcode" id="ecomponentsoort-kolomcode" name="kolomcode" data-cy="kolomcode" type="text" />
              <ValidatedField
                label="Regeling"
                id="ecomponentsoort-regeling"
                name="regeling"
                data-cy="regeling"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Regelingcode"
                id="ecomponentsoort-regelingcode"
                name="regelingcode"
                data-cy="regelingcode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ecomponentsoort" replace color="info">
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

export default EcomponentsoortUpdate;
