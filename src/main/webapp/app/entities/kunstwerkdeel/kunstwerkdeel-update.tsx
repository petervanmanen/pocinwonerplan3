import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKunstwerkdeel } from 'app/shared/model/kunstwerkdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './kunstwerkdeel.reducer';

export const KunstwerkdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kunstwerkdeelEntity = useAppSelector(state => state.kunstwerkdeel.entity);
  const loading = useAppSelector(state => state.kunstwerkdeel.loading);
  const updating = useAppSelector(state => state.kunstwerkdeel.updating);
  const updateSuccess = useAppSelector(state => state.kunstwerkdeel.updateSuccess);

  const handleClose = () => {
    navigate('/kunstwerkdeel');
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
      ...kunstwerkdeelEntity,
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
          ...kunstwerkdeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kunstwerkdeel.home.createOrEditLabel" data-cy="KunstwerkdeelCreateUpdateHeading">
            Create or edit a Kunstwerkdeel
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
                <ValidatedField name="id" required readOnly id="kunstwerkdeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidkunstwerkdeel"
                id="kunstwerkdeel-datumbegingeldigheidkunstwerkdeel"
                name="datumbegingeldigheidkunstwerkdeel"
                data-cy="datumbegingeldigheidkunstwerkdeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidkunstwerkdeel"
                id="kunstwerkdeel-datumeindegeldigheidkunstwerkdeel"
                name="datumeindegeldigheidkunstwerkdeel"
                data-cy="datumeindegeldigheidkunstwerkdeel"
                type="date"
              />
              <ValidatedField
                label="Geometriekunstwerkdeel"
                id="kunstwerkdeel-geometriekunstwerkdeel"
                name="geometriekunstwerkdeel"
                data-cy="geometriekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Identificatiekunstwerkdeel"
                id="kunstwerkdeel-identificatiekunstwerkdeel"
                name="identificatiekunstwerkdeel"
                data-cy="identificatiekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometriekunstwerkdeel"
                id="kunstwerkdeel-lod0geometriekunstwerkdeel"
                name="lod0geometriekunstwerkdeel"
                data-cy="lod0geometriekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 1 Geometriekunstwerkdeel"
                id="kunstwerkdeel-lod1geometriekunstwerkdeel"
                name="lod1geometriekunstwerkdeel"
                data-cy="lod1geometriekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 2 Geometriekunstwerkdeel"
                id="kunstwerkdeel-lod2geometriekunstwerkdeel"
                name="lod2geometriekunstwerkdeel"
                data-cy="lod2geometriekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 3 Geometriekunstwerkdeel"
                id="kunstwerkdeel-lod3geometriekunstwerkdeel"
                name="lod3geometriekunstwerkdeel"
                data-cy="lod3geometriekunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingkunstwerkdeel"
                id="kunstwerkdeel-relatievehoogteliggingkunstwerkdeel"
                name="relatievehoogteliggingkunstwerkdeel"
                data-cy="relatievehoogteliggingkunstwerkdeel"
                type="text"
              />
              <ValidatedField
                label="Statuskunstwerkdeel"
                id="kunstwerkdeel-statuskunstwerkdeel"
                name="statuskunstwerkdeel"
                data-cy="statuskunstwerkdeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kunstwerkdeel" replace color="info">
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

export default KunstwerkdeelUpdate;
