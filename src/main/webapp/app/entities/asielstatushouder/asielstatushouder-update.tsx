import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGemeente } from 'app/shared/model/gemeente.model';
import { getEntities as getGemeentes } from 'app/entities/gemeente/gemeente.reducer';
import { IAsielstatushouder } from 'app/shared/model/asielstatushouder.model';
import { getEntity, updateEntity, createEntity, reset } from './asielstatushouder.reducer';

export const AsielstatushouderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gemeentes = useAppSelector(state => state.gemeente.entities);
  const asielstatushouderEntity = useAppSelector(state => state.asielstatushouder.entity);
  const loading = useAppSelector(state => state.asielstatushouder.loading);
  const updating = useAppSelector(state => state.asielstatushouder.updating);
  const updateSuccess = useAppSelector(state => state.asielstatushouder.updateSuccess);

  const handleClose = () => {
    navigate('/asielstatushouder');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGemeentes({}));
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
      ...asielstatushouderEntity,
      ...values,
      isgekoppeldaanGemeente: gemeentes.find(it => it.id.toString() === values.isgekoppeldaanGemeente?.toString()),
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
          ...asielstatushouderEntity,
          isgekoppeldaanGemeente: asielstatushouderEntity?.isgekoppeldaanGemeente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.asielstatushouder.home.createOrEditLabel" data-cy="AsielstatushouderCreateUpdateHeading">
            Create or edit a Asielstatushouder
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
                <ValidatedField name="id" required readOnly id="asielstatushouder-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Digidaangevraagd"
                id="asielstatushouder-digidaangevraagd"
                name="digidaangevraagd"
                data-cy="digidaangevraagd"
                type="text"
              />
              <ValidatedField
                label="Emailadresverblijfazc"
                id="asielstatushouder-emailadresverblijfazc"
                name="emailadresverblijfazc"
                data-cy="emailadresverblijfazc"
                type="text"
              />
              <ValidatedField
                label="Isgekoppeldaan"
                id="asielstatushouder-isgekoppeldaan"
                name="isgekoppeldaan"
                data-cy="isgekoppeldaan"
                type="text"
              />
              <ValidatedField
                label="Landrijbewijs"
                id="asielstatushouder-landrijbewijs"
                name="landrijbewijs"
                data-cy="landrijbewijs"
                type="text"
              />
              <ValidatedField label="Rijbewijs" id="asielstatushouder-rijbewijs" name="rijbewijs" data-cy="rijbewijs" type="text" />
              <ValidatedField
                label="Telefoonnummerverblijfazc"
                id="asielstatushouder-telefoonnummerverblijfazc"
                name="telefoonnummerverblijfazc"
                data-cy="telefoonnummerverblijfazc"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                id="asielstatushouder-isgekoppeldaanGemeente"
                name="isgekoppeldaanGemeente"
                data-cy="isgekoppeldaanGemeente"
                label="Isgekoppeldaan Gemeente"
                type="select"
              >
                <option value="" key="0" />
                {gemeentes
                  ? gemeentes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/asielstatushouder" replace color="info">
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

export default AsielstatushouderUpdate;
