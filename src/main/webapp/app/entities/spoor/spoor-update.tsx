import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVlak } from 'app/shared/model/vlak.model';
import { getEntities as getVlaks } from 'app/entities/vlak/vlak.reducer';
import { ISpoor } from 'app/shared/model/spoor.model';
import { getEntity, updateEntity, createEntity, reset } from './spoor.reducer';

export const SpoorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vlaks = useAppSelector(state => state.vlak.entities);
  const spoorEntity = useAppSelector(state => state.spoor.entity);
  const loading = useAppSelector(state => state.spoor.loading);
  const updating = useAppSelector(state => state.spoor.updating);
  const updateSuccess = useAppSelector(state => state.spoor.updateSuccess);

  const handleClose = () => {
    navigate('/spoor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVlaks({}));
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
      ...spoorEntity,
      ...values,
      heeftVlak: vlaks.find(it => it.id.toString() === values.heeftVlak?.toString()),
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
          ...spoorEntity,
          heeftVlak: spoorEntity?.heeftVlak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.spoor.home.createOrEditLabel" data-cy="SpoorCreateUpdateHeading">
            Create or edit a Spoor
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="spoor-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aard" id="spoor-aard" name="aard" data-cy="aard" type="text" />
              <ValidatedField label="Beschrijving" id="spoor-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField
                label="Datering"
                id="spoor-datering"
                name="datering"
                data-cy="datering"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Datum" id="spoor-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                label="Hoogteboven"
                id="spoor-hoogteboven"
                name="hoogteboven"
                data-cy="hoogteboven"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Hoogteonder"
                id="spoor-hoogteonder"
                name="hoogteonder"
                data-cy="hoogteonder"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Key" id="spoor-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keyvlak" id="spoor-keyvlak" name="keyvlak" data-cy="keyvlak" type="text" />
              <ValidatedField label="Projectcd" id="spoor-projectcd" name="projectcd" data-cy="projectcd" type="text" />
              <ValidatedField label="Putnummer" id="spoor-putnummer" name="putnummer" data-cy="putnummer" type="text" />
              <ValidatedField label="Spoornummer" id="spoor-spoornummer" name="spoornummer" data-cy="spoornummer" type="text" />
              <ValidatedField label="Vlaknummer" id="spoor-vlaknummer" name="vlaknummer" data-cy="vlaknummer" type="text" />
              <ValidatedField
                label="Vorm"
                id="spoor-vorm"
                name="vorm"
                data-cy="vorm"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="spoor-heeftVlak" name="heeftVlak" data-cy="heeftVlak" label="Heeft Vlak" type="select">
                <option value="" key="0" />
                {vlaks
                  ? vlaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/spoor" replace color="info">
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

export default SpoorUpdate;
