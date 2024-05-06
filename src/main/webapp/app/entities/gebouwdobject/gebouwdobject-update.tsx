import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGebouwdobject } from 'app/shared/model/gebouwdobject.model';
import { getEntity, updateEntity, createEntity, reset } from './gebouwdobject.reducer';

export const GebouwdobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gebouwdobjectEntity = useAppSelector(state => state.gebouwdobject.entity);
  const loading = useAppSelector(state => state.gebouwdobject.loading);
  const updating = useAppSelector(state => state.gebouwdobject.updating);
  const updateSuccess = useAppSelector(state => state.gebouwdobject.updateSuccess);

  const handleClose = () => {
    navigate('/gebouwdobject');
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
      ...gebouwdobjectEntity,
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
          ...gebouwdobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gebouwdobject.home.createOrEditLabel" data-cy="GebouwdobjectCreateUpdateHeading">
            Create or edit a Gebouwdobject
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
                <ValidatedField name="id" required readOnly id="gebouwdobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bouwkundigebestemmingactueel"
                id="gebouwdobject-bouwkundigebestemmingactueel"
                name="bouwkundigebestemmingactueel"
                data-cy="bouwkundigebestemmingactueel"
                type="text"
              />
              <ValidatedField label="Brutoinhoud" id="gebouwdobject-brutoinhoud" name="brutoinhoud" data-cy="brutoinhoud" type="text" />
              <ValidatedField
                label="Identificatie"
                id="gebouwdobject-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Inwinningoppervlakte"
                id="gebouwdobject-inwinningoppervlakte"
                name="inwinningoppervlakte"
                data-cy="inwinningoppervlakte"
                type="text"
              />
              <ValidatedField
                label="Oppervlakteobject"
                id="gebouwdobject-oppervlakteobject"
                name="oppervlakteobject"
                data-cy="oppervlakteobject"
                type="text"
              />
              <ValidatedField
                label="Statusvoortgangbouw"
                id="gebouwdobject-statusvoortgangbouw"
                name="statusvoortgangbouw"
                data-cy="statusvoortgangbouw"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gebouwdobject" replace color="info">
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

export default GebouwdobjectUpdate;
