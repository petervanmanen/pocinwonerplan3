import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';
import { getEntities as getBedrijfsprocestypes } from 'app/entities/bedrijfsprocestype/bedrijfsprocestype.reducer';
import { IDeelprocestype } from 'app/shared/model/deelprocestype.model';
import { getEntity, updateEntity, createEntity, reset } from './deelprocestype.reducer';

export const DeelprocestypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bedrijfsprocestypes = useAppSelector(state => state.bedrijfsprocestype.entities);
  const deelprocestypeEntity = useAppSelector(state => state.deelprocestype.entity);
  const loading = useAppSelector(state => state.deelprocestype.loading);
  const updating = useAppSelector(state => state.deelprocestype.updating);
  const updateSuccess = useAppSelector(state => state.deelprocestype.updateSuccess);

  const handleClose = () => {
    navigate('/deelprocestype');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBedrijfsprocestypes({}));
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
      ...deelprocestypeEntity,
      ...values,
      isdeelvanBedrijfsprocestype: bedrijfsprocestypes.find(it => it.id.toString() === values.isdeelvanBedrijfsprocestype?.toString()),
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
          ...deelprocestypeEntity,
          isdeelvanBedrijfsprocestype: deelprocestypeEntity?.isdeelvanBedrijfsprocestype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.deelprocestype.home.createOrEditLabel" data-cy="DeelprocestypeCreateUpdateHeading">
            Create or edit a Deelprocestype
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
                <ValidatedField name="id" required readOnly id="deelprocestype-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Omschrijving"
                id="deelprocestype-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                id="deelprocestype-isdeelvanBedrijfsprocestype"
                name="isdeelvanBedrijfsprocestype"
                data-cy="isdeelvanBedrijfsprocestype"
                label="Isdeelvan Bedrijfsprocestype"
                type="select"
                required
              >
                <option value="" key="0" />
                {bedrijfsprocestypes
                  ? bedrijfsprocestypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deelprocestype" replace color="info">
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

export default DeelprocestypeUpdate;
