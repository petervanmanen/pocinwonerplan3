import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDigitaalbestand } from 'app/shared/model/digitaalbestand.model';
import { getEntity, updateEntity, createEntity, reset } from './digitaalbestand.reducer';

export const DigitaalbestandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const digitaalbestandEntity = useAppSelector(state => state.digitaalbestand.entity);
  const loading = useAppSelector(state => state.digitaalbestand.loading);
  const updating = useAppSelector(state => state.digitaalbestand.updating);
  const updateSuccess = useAppSelector(state => state.digitaalbestand.updateSuccess);

  const handleClose = () => {
    navigate('/digitaalbestand');
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
      ...digitaalbestandEntity,
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
          ...digitaalbestandEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.digitaalbestand.home.createOrEditLabel" data-cy="DigitaalbestandCreateUpdateHeading">
            Create or edit a Digitaalbestand
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
                <ValidatedField name="id" required readOnly id="digitaalbestand-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Blob" id="digitaalbestand-blob" name="blob" data-cy="blob" type="text" />
              <ValidatedField
                label="Mimetype"
                id="digitaalbestand-mimetype"
                name="mimetype"
                data-cy="mimetype"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="digitaalbestand-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="digitaalbestand-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/digitaalbestand" replace color="info">
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

export default DigitaalbestandUpdate;
