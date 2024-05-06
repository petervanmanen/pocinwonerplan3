import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEobject } from 'app/shared/model/eobject.model';
import { getEntity, updateEntity, createEntity, reset } from './eobject.reducer';

export const EobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const eobjectEntity = useAppSelector(state => state.eobject.entity);
  const loading = useAppSelector(state => state.eobject.loading);
  const updating = useAppSelector(state => state.eobject.updating);
  const updateSuccess = useAppSelector(state => state.eobject.updateSuccess);

  const handleClose = () => {
    navigate('/eobject');
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
      ...eobjectEntity,
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
          ...eobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.eobject.home.createOrEditLabel" data-cy="EobjectCreateUpdateHeading">
            Create or edit a Eobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="eobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Adresbinnenland"
                id="eobject-adresbinnenland"
                name="adresbinnenland"
                data-cy="adresbinnenland"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland"
                id="eobject-adresbuitenland"
                name="adresbuitenland"
                data-cy="adresbuitenland"
                type="text"
              />
              <ValidatedField label="Domein" id="eobject-domein" name="domein" data-cy="domein" type="text" />
              <ValidatedField label="Geometrie" id="eobject-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Identificatie" id="eobject-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField
                label="Indicatierisico"
                id="eobject-indicatierisico"
                name="indicatierisico"
                data-cy="indicatierisico"
                type="text"
              />
              <ValidatedField
                label="Kadastraleaanduiding"
                id="eobject-kadastraleaanduiding"
                name="kadastraleaanduiding"
                data-cy="kadastraleaanduiding"
                type="text"
              />
              <ValidatedField label="Naam" id="eobject-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Eobjecttype" id="eobject-eobjecttype" name="eobjecttype" data-cy="eobjecttype" type="text" />
              <ValidatedField label="Toelichting" id="eobject-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/eobject" replace color="info">
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

export default EobjectUpdate;
