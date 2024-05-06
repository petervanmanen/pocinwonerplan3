import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverigbouwwerk } from 'app/shared/model/overigbouwwerk.model';
import { getEntity, updateEntity, createEntity, reset } from './overigbouwwerk.reducer';

export const OverigbouwwerkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overigbouwwerkEntity = useAppSelector(state => state.overigbouwwerk.entity);
  const loading = useAppSelector(state => state.overigbouwwerk.loading);
  const updating = useAppSelector(state => state.overigbouwwerk.updating);
  const updateSuccess = useAppSelector(state => state.overigbouwwerk.updateSuccess);

  const handleClose = () => {
    navigate('/overigbouwwerk');
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
      ...overigbouwwerkEntity,
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
          ...overigbouwwerkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overigbouwwerk.home.createOrEditLabel" data-cy="OverigbouwwerkCreateUpdateHeading">
            Create or edit a Overigbouwwerk
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
                <ValidatedField name="id" required readOnly id="overigbouwwerk-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidoverigbouwwerk"
                id="overigbouwwerk-datumbegingeldigheidoverigbouwwerk"
                name="datumbegingeldigheidoverigbouwwerk"
                data-cy="datumbegingeldigheidoverigbouwwerk"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidoverigbouwwerk"
                id="overigbouwwerk-datumeindegeldigheidoverigbouwwerk"
                name="datumeindegeldigheidoverigbouwwerk"
                data-cy="datumeindegeldigheidoverigbouwwerk"
                type="date"
              />
              <ValidatedField
                label="Geometrieoverigbouwwerk"
                id="overigbouwwerk-geometrieoverigbouwwerk"
                name="geometrieoverigbouwwerk"
                data-cy="geometrieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Identificatieoverigbouwwerk"
                id="overigbouwwerk-identificatieoverigbouwwerk"
                name="identificatieoverigbouwwerk"
                data-cy="identificatieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrieoverigbouwwerk"
                id="overigbouwwerk-lod0geometrieoverigbouwwerk"
                name="lod0geometrieoverigbouwwerk"
                data-cy="lod0geometrieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Lod 1 Geometrieoverigbouwwerk"
                id="overigbouwwerk-lod1geometrieoverigbouwwerk"
                name="lod1geometrieoverigbouwwerk"
                data-cy="lod1geometrieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Lod 2 Geometrieoverigbouwwerk"
                id="overigbouwwerk-lod2geometrieoverigbouwwerk"
                name="lod2geometrieoverigbouwwerk"
                data-cy="lod2geometrieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Lod 3 Geometrieoverigbouwwerk"
                id="overigbouwwerk-lod3geometrieoverigbouwwerk"
                name="lod3geometrieoverigbouwwerk"
                data-cy="lod3geometrieoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingoverigbouwwerk"
                id="overigbouwwerk-relatievehoogteliggingoverigbouwwerk"
                name="relatievehoogteliggingoverigbouwwerk"
                data-cy="relatievehoogteliggingoverigbouwwerk"
                type="text"
              />
              <ValidatedField
                label="Statusoverigbouwwerk"
                id="overigbouwwerk-statusoverigbouwwerk"
                name="statusoverigbouwwerk"
                data-cy="statusoverigbouwwerk"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overigbouwwerk" replace color="info">
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

export default OverigbouwwerkUpdate;
