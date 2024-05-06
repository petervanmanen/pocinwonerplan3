import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivasoort } from 'app/shared/model/activasoort.model';
import { getEntities as getActivasoorts } from 'app/entities/activasoort/activasoort.reducer';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IActiva } from 'app/shared/model/activa.model';
import { getEntity, updateEntity, createEntity, reset } from './activa.reducer';

export const ActivaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activasoorts = useAppSelector(state => state.activasoort.entities);
  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const activaEntity = useAppSelector(state => state.activa.entity);
  const loading = useAppSelector(state => state.activa.loading);
  const updating = useAppSelector(state => state.activa.updating);
  const updateSuccess = useAppSelector(state => state.activa.updateSuccess);

  const handleClose = () => {
    navigate('/activa');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivasoorts({}));
    dispatch(getHoofdrekenings({}));
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
      ...activaEntity,
      ...values,
      issoortActivasoort: activasoorts.find(it => it.id.toString() === values.issoortActivasoort?.toString()),
      heeftHoofdrekenings: mapIdList(values.heeftHoofdrekenings),
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
          ...activaEntity,
          issoortActivasoort: activaEntity?.issoortActivasoort?.id,
          heeftHoofdrekenings: activaEntity?.heeftHoofdrekenings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.activa.home.createOrEditLabel" data-cy="ActivaCreateUpdateHeading">
            Create or edit a Activa
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="activa-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="activa-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="activa-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="activa-issoortActivasoort"
                name="issoortActivasoort"
                data-cy="issoortActivasoort"
                label="Issoort Activasoort"
                type="select"
              >
                <option value="" key="0" />
                {activasoorts
                  ? activasoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Hoofdrekening"
                id="activa-heeftHoofdrekening"
                data-cy="heeftHoofdrekening"
                type="select"
                multiple
                name="heeftHoofdrekenings"
              >
                <option value="" key="0" />
                {hoofdrekenings
                  ? hoofdrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activa" replace color="info">
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

export default ActivaUpdate;
