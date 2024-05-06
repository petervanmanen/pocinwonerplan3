import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILand } from 'app/shared/model/land.model';
import { getEntity, updateEntity, createEntity, reset } from './land.reducer';

export const LandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const landEntity = useAppSelector(state => state.land.entity);
  const loading = useAppSelector(state => state.land.loading);
  const updating = useAppSelector(state => state.land.updating);
  const updateSuccess = useAppSelector(state => state.land.updateSuccess);

  const handleClose = () => {
    navigate('/land');
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
      ...landEntity,
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
          ...landEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.land.home.createOrEditLabel" data-cy="LandCreateUpdateHeading">
            Create or edit a Land
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="land-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumeindefictief"
                id="land-datumeindefictief"
                name="datumeindefictief"
                data-cy="datumeindefictief"
                check
                type="checkbox"
              />
              <ValidatedField label="Datumeindeland" id="land-datumeindeland" name="datumeindeland" data-cy="datumeindeland" type="text" />
              <ValidatedField
                label="Datumingangland"
                id="land-datumingangland"
                name="datumingangland"
                data-cy="datumingangland"
                type="text"
              />
              <ValidatedField label="Landcode" id="land-landcode" name="landcode" data-cy="landcode" type="text" />
              <ValidatedField
                label="Landcodeisodrieletterig"
                id="land-landcodeisodrieletterig"
                name="landcodeisodrieletterig"
                data-cy="landcodeisodrieletterig"
                type="text"
              />
              <ValidatedField
                label="Landcodeisotweeletterig"
                id="land-landcodeisotweeletterig"
                name="landcodeisotweeletterig"
                data-cy="landcodeisotweeletterig"
                type="text"
              />
              <ValidatedField label="Landnaam" id="land-landnaam" name="landnaam" data-cy="landnaam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/land" replace color="info">
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

export default LandUpdate;
