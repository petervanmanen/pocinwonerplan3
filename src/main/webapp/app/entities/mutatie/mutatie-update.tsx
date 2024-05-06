import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IMutatie } from 'app/shared/model/mutatie.model';
import { getEntity, updateEntity, createEntity, reset } from './mutatie.reducer';

export const MutatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const mutatieEntity = useAppSelector(state => state.mutatie.entity);
  const loading = useAppSelector(state => state.mutatie.loading);
  const updating = useAppSelector(state => state.mutatie.updating);
  const updateSuccess = useAppSelector(state => state.mutatie.updateSuccess);

  const handleClose = () => {
    navigate('/mutatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHoofdrekenings({}));
    dispatch(getKostenplaats({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...mutatieEntity,
      ...values,
      vanHoofdrekening: hoofdrekenings.find(it => it.id.toString() === values.vanHoofdrekening?.toString()),
      naarHoofdrekening: hoofdrekenings.find(it => it.id.toString() === values.naarHoofdrekening?.toString()),
      heeftbetrekkingopKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftbetrekkingopKostenplaats?.toString()),
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
          ...mutatieEntity,
          vanHoofdrekening: mutatieEntity?.vanHoofdrekening?.id,
          naarHoofdrekening: mutatieEntity?.naarHoofdrekening?.id,
          heeftbetrekkingopKostenplaats: mutatieEntity?.heeftbetrekkingopKostenplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.mutatie.home.createOrEditLabel" data-cy="MutatieCreateUpdateHeading">
            Create or edit a Mutatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="mutatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="mutatie-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datum" id="mutatie-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField
                id="mutatie-vanHoofdrekening"
                name="vanHoofdrekening"
                data-cy="vanHoofdrekening"
                label="Van Hoofdrekening"
                type="select"
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
              <ValidatedField
                id="mutatie-naarHoofdrekening"
                name="naarHoofdrekening"
                data-cy="naarHoofdrekening"
                label="Naar Hoofdrekening"
                type="select"
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
              <ValidatedField
                id="mutatie-heeftbetrekkingopKostenplaats"
                name="heeftbetrekkingopKostenplaats"
                data-cy="heeftbetrekkingopKostenplaats"
                label="Heeftbetrekkingop Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mutatie" replace color="info">
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

export default MutatieUpdate;
