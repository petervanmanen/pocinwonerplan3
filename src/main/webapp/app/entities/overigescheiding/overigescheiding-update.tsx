import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverigescheiding } from 'app/shared/model/overigescheiding.model';
import { getEntity, updateEntity, createEntity, reset } from './overigescheiding.reducer';

export const OverigescheidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overigescheidingEntity = useAppSelector(state => state.overigescheiding.entity);
  const loading = useAppSelector(state => state.overigescheiding.loading);
  const updating = useAppSelector(state => state.overigescheiding.updating);
  const updateSuccess = useAppSelector(state => state.overigescheiding.updateSuccess);

  const handleClose = () => {
    navigate('/overigescheiding');
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
      ...overigescheidingEntity,
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
          ...overigescheidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overigescheiding.home.createOrEditLabel" data-cy="OverigescheidingCreateUpdateHeading">
            Create or edit a Overigescheiding
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
                <ValidatedField name="id" required readOnly id="overigescheiding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidoverigescheiding"
                id="overigescheiding-datumbegingeldigheidoverigescheiding"
                name="datumbegingeldigheidoverigescheiding"
                data-cy="datumbegingeldigheidoverigescheiding"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidoverigescheiding"
                id="overigescheiding-datumeindegeldigheidoverigescheiding"
                name="datumeindegeldigheidoverigescheiding"
                data-cy="datumeindegeldigheidoverigescheiding"
                type="date"
              />
              <ValidatedField
                label="Geometrieoverigescheiding"
                id="overigescheiding-geometrieoverigescheiding"
                name="geometrieoverigescheiding"
                data-cy="geometrieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Identificatieoverigescheiding"
                id="overigescheiding-identificatieoverigescheiding"
                name="identificatieoverigescheiding"
                data-cy="identificatieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrieoverigescheiding"
                id="overigescheiding-lod0geometrieoverigescheiding"
                name="lod0geometrieoverigescheiding"
                data-cy="lod0geometrieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Lod 1 Geometrieoverigescheiding"
                id="overigescheiding-lod1geometrieoverigescheiding"
                name="lod1geometrieoverigescheiding"
                data-cy="lod1geometrieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Lod 2 Geometrieoverigescheiding"
                id="overigescheiding-lod2geometrieoverigescheiding"
                name="lod2geometrieoverigescheiding"
                data-cy="lod2geometrieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Lod 3 Geometrieoverigescheiding"
                id="overigescheiding-lod3geometrieoverigescheiding"
                name="lod3geometrieoverigescheiding"
                data-cy="lod3geometrieoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingoverigescheiding"
                id="overigescheiding-relatievehoogteliggingoverigescheiding"
                name="relatievehoogteliggingoverigescheiding"
                data-cy="relatievehoogteliggingoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Statusoverigescheiding"
                id="overigescheiding-statusoverigescheiding"
                name="statusoverigescheiding"
                data-cy="statusoverigescheiding"
                type="text"
              />
              <ValidatedField
                label="Typeoverigescheiding"
                id="overigescheiding-typeoverigescheiding"
                name="typeoverigescheiding"
                data-cy="typeoverigescheiding"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overigescheiding" replace color="info">
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

export default OverigescheidingUpdate;
