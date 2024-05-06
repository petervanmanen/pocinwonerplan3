import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStremming } from 'app/shared/model/stremming.model';
import { getEntities as getStremmings } from 'app/entities/stremming/stremming.reducer';
import { IWegdeel } from 'app/shared/model/wegdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './wegdeel.reducer';

export const WegdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stremmings = useAppSelector(state => state.stremming.entities);
  const wegdeelEntity = useAppSelector(state => state.wegdeel.entity);
  const loading = useAppSelector(state => state.wegdeel.loading);
  const updating = useAppSelector(state => state.wegdeel.updating);
  const updateSuccess = useAppSelector(state => state.wegdeel.updateSuccess);

  const handleClose = () => {
    navigate('/wegdeel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStremmings({}));
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
      ...wegdeelEntity,
      ...values,
      betreftStremmings: mapIdList(values.betreftStremmings),
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
          ...wegdeelEntity,
          betreftStremmings: wegdeelEntity?.betreftStremmings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wegdeel.home.createOrEditLabel" data-cy="WegdeelCreateUpdateHeading">
            Create or edit a Wegdeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="wegdeel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheidwegdeel"
                id="wegdeel-datumbegingeldigheidwegdeel"
                name="datumbegingeldigheidwegdeel"
                data-cy="datumbegingeldigheidwegdeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidwegdeel"
                id="wegdeel-datumeindegeldigheidwegdeel"
                name="datumeindegeldigheidwegdeel"
                data-cy="datumeindegeldigheidwegdeel"
                type="date"
              />
              <ValidatedField
                label="Functiewegdeel"
                id="wegdeel-functiewegdeel"
                name="functiewegdeel"
                data-cy="functiewegdeel"
                type="text"
              />
              <ValidatedField
                label="Fysiekvoorkomenwegdeel"
                id="wegdeel-fysiekvoorkomenwegdeel"
                name="fysiekvoorkomenwegdeel"
                data-cy="fysiekvoorkomenwegdeel"
                type="text"
              />
              <ValidatedField
                label="Geometriewegdeel"
                id="wegdeel-geometriewegdeel"
                name="geometriewegdeel"
                data-cy="geometriewegdeel"
                type="text"
              />
              <ValidatedField
                label="Identificatiewegdeel"
                id="wegdeel-identificatiewegdeel"
                name="identificatiewegdeel"
                data-cy="identificatiewegdeel"
                type="text"
              />
              <ValidatedField
                label="Kruinlijngeometriewegdeel"
                id="wegdeel-kruinlijngeometriewegdeel"
                name="kruinlijngeometriewegdeel"
                data-cy="kruinlijngeometriewegdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometriewegdeel"
                id="wegdeel-lod0geometriewegdeel"
                name="lod0geometriewegdeel"
                data-cy="lod0geometriewegdeel"
                type="text"
              />
              <ValidatedField
                label="Plusfunctiewegdeel"
                id="wegdeel-plusfunctiewegdeel"
                name="plusfunctiewegdeel"
                data-cy="plusfunctiewegdeel"
                type="text"
              />
              <ValidatedField
                label="Plusfysiekvoorkomenwegdeel"
                id="wegdeel-plusfysiekvoorkomenwegdeel"
                name="plusfysiekvoorkomenwegdeel"
                data-cy="plusfysiekvoorkomenwegdeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingwegdeel"
                id="wegdeel-relatievehoogteliggingwegdeel"
                name="relatievehoogteliggingwegdeel"
                data-cy="relatievehoogteliggingwegdeel"
                type="text"
              />
              <ValidatedField label="Statuswegdeel" id="wegdeel-statuswegdeel" name="statuswegdeel" data-cy="statuswegdeel" type="text" />
              <ValidatedField
                label="Wegdeeloptalud"
                id="wegdeel-wegdeeloptalud"
                name="wegdeeloptalud"
                data-cy="wegdeeloptalud"
                type="text"
              />
              <ValidatedField
                label="Betreft Stremming"
                id="wegdeel-betreftStremming"
                data-cy="betreftStremming"
                type="select"
                multiple
                name="betreftStremmings"
              >
                <option value="" key="0" />
                {stremmings
                  ? stremmings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wegdeel" replace color="info">
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

export default WegdeelUpdate;
