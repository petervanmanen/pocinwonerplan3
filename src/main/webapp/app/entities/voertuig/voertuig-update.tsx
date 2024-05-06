import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoertuig } from 'app/shared/model/voertuig.model';
import { getEntity, updateEntity, createEntity, reset } from './voertuig.reducer';

export const VoertuigUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voertuigEntity = useAppSelector(state => state.voertuig.entity);
  const loading = useAppSelector(state => state.voertuig.loading);
  const updating = useAppSelector(state => state.voertuig.updating);
  const updateSuccess = useAppSelector(state => state.voertuig.updateSuccess);

  const handleClose = () => {
    navigate('/voertuig');
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
      ...voertuigEntity,
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
          ...voertuigEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.voertuig.home.createOrEditLabel" data-cy="VoertuigCreateUpdateHeading">
            Create or edit a Voertuig
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="voertuig-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Kenteken" id="voertuig-kenteken" name="kenteken" data-cy="kenteken" type="text" />
              <ValidatedField label="Kleur" id="voertuig-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField label="Land" id="voertuig-land" name="land" data-cy="land" type="text" />
              <ValidatedField label="Merk" id="voertuig-merk" name="merk" data-cy="merk" type="text" />
              <ValidatedField label="Type" id="voertuig-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/voertuig" replace color="info">
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

export default VoertuigUpdate;
