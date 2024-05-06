import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoorzieningsoort } from 'app/shared/model/voorzieningsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './voorzieningsoort.reducer';

export const VoorzieningsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voorzieningsoortEntity = useAppSelector(state => state.voorzieningsoort.entity);
  const loading = useAppSelector(state => state.voorzieningsoort.loading);
  const updating = useAppSelector(state => state.voorzieningsoort.updating);
  const updateSuccess = useAppSelector(state => state.voorzieningsoort.updateSuccess);

  const handleClose = () => {
    navigate('/voorzieningsoort');
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
      ...voorzieningsoortEntity,
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
          ...voorzieningsoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.voorzieningsoort.home.createOrEditLabel" data-cy="VoorzieningsoortCreateUpdateHeading">
            Create or edit a Voorzieningsoort
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
                <ValidatedField name="id" required readOnly id="voorzieningsoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Code" id="voorzieningsoort-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Naam" id="voorzieningsoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="voorzieningsoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Productcategorie"
                id="voorzieningsoort-productcategorie"
                name="productcategorie"
                data-cy="productcategorie"
                type="text"
              />
              <ValidatedField
                label="Productcategoriecode"
                id="voorzieningsoort-productcategoriecode"
                name="productcategoriecode"
                data-cy="productcategoriecode"
                type="text"
              />
              <ValidatedField
                label="Productcode"
                id="voorzieningsoort-productcode"
                name="productcode"
                data-cy="productcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Wet" id="voorzieningsoort-wet" name="wet" data-cy="wet" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/voorzieningsoort" replace color="info">
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

export default VoorzieningsoortUpdate;
