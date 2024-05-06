import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFunctie } from 'app/shared/model/functie.model';
import { getEntities as getFuncties } from 'app/entities/functie/functie.reducer';
import { IUren } from 'app/shared/model/uren.model';
import { getEntities as getUrens } from 'app/entities/uren/uren.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { IInzet } from 'app/shared/model/inzet.model';
import { getEntities as getInzets } from 'app/entities/inzet/inzet.reducer';
import { IDienstverband } from 'app/shared/model/dienstverband.model';
import { getEntity, updateEntity, createEntity, reset } from './dienstverband.reducer';

export const DienstverbandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const functies = useAppSelector(state => state.functie.entities);
  const urens = useAppSelector(state => state.uren.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const inzets = useAppSelector(state => state.inzet.entities);
  const dienstverbandEntity = useAppSelector(state => state.dienstverband.entity);
  const loading = useAppSelector(state => state.dienstverband.loading);
  const updating = useAppSelector(state => state.dienstverband.updating);
  const updateSuccess = useAppSelector(state => state.dienstverband.updateSuccess);

  const handleClose = () => {
    navigate('/dienstverband');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFuncties({}));
    dispatch(getUrens({}));
    dispatch(getWerknemers({}));
    dispatch(getInzets({}));
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
    if (values.salaris !== undefined && typeof values.salaris !== 'number') {
      values.salaris = Number(values.salaris);
    }

    const entity = {
      ...dienstverbandEntity,
      ...values,
      dienstverbandconformfunctieFunctie: functies.find(it => it.id.toString() === values.dienstverbandconformfunctieFunctie?.toString()),
      aantalvolgensinzetUren: urens.find(it => it.id.toString() === values.aantalvolgensinzetUren?.toString()),
      medewerkerheeftdienstverbandWerknemer: werknemers.find(
        it => it.id.toString() === values.medewerkerheeftdienstverbandWerknemer?.toString(),
      ),
      aantalvolgensinzetInzet: inzets.find(it => it.id.toString() === values.aantalvolgensinzetInzet?.toString()),
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
          ...dienstverbandEntity,
          dienstverbandconformfunctieFunctie: dienstverbandEntity?.dienstverbandconformfunctieFunctie?.id,
          aantalvolgensinzetUren: dienstverbandEntity?.aantalvolgensinzetUren?.id,
          medewerkerheeftdienstverbandWerknemer: dienstverbandEntity?.medewerkerheeftdienstverbandWerknemer?.id,
          aantalvolgensinzetInzet: dienstverbandEntity?.aantalvolgensinzetInzet?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.dienstverband.home.createOrEditLabel" data-cy="DienstverbandCreateUpdateHeading">
            Create or edit a Dienstverband
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
                <ValidatedField name="id" required readOnly id="dienstverband-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="dienstverband-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="dienstverband-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Periodiek" id="dienstverband-periodiek" name="periodiek" data-cy="periodiek" type="text" />
              <ValidatedField label="Salaris" id="dienstverband-salaris" name="salaris" data-cy="salaris" type="text" />
              <ValidatedField label="Schaal" id="dienstverband-schaal" name="schaal" data-cy="schaal" type="text" />
              <ValidatedField label="Urenperweek" id="dienstverband-urenperweek" name="urenperweek" data-cy="urenperweek" type="text" />
              <ValidatedField
                id="dienstverband-dienstverbandconformfunctieFunctie"
                name="dienstverbandconformfunctieFunctie"
                data-cy="dienstverbandconformfunctieFunctie"
                label="Dienstverbandconformfunctie Functie"
                type="select"
              >
                <option value="" key="0" />
                {functies
                  ? functies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dienstverband-aantalvolgensinzetUren"
                name="aantalvolgensinzetUren"
                data-cy="aantalvolgensinzetUren"
                label="Aantalvolgensinzet Uren"
                type="select"
              >
                <option value="" key="0" />
                {urens
                  ? urens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dienstverband-medewerkerheeftdienstverbandWerknemer"
                name="medewerkerheeftdienstverbandWerknemer"
                data-cy="medewerkerheeftdienstverbandWerknemer"
                label="Medewerkerheeftdienstverband Werknemer"
                type="select"
                required
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="dienstverband-aantalvolgensinzetInzet"
                name="aantalvolgensinzetInzet"
                data-cy="aantalvolgensinzetInzet"
                label="Aantalvolgensinzet Inzet"
                type="select"
              >
                <option value="" key="0" />
                {inzets
                  ? inzets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dienstverband" replace color="info">
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

export default DienstverbandUpdate;
