import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeschikking } from 'app/shared/model/beschikking.model';
import { getEntities as getBeschikkings } from 'app/entities/beschikking/beschikking.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IToewijzing } from 'app/shared/model/toewijzing.model';
import { getEntities as getToewijzings } from 'app/entities/toewijzing/toewijzing.reducer';
import { IVoorziening } from 'app/shared/model/voorziening.model';
import { getEntities as getVoorzienings } from 'app/entities/voorziening/voorziening.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { ILevering } from 'app/shared/model/levering.model';
import { getEntity, updateEntity, createEntity, reset } from './levering.reducer';

export const LeveringUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beschikkings = useAppSelector(state => state.beschikking.entities);
  const clients = useAppSelector(state => state.client.entities);
  const toewijzings = useAppSelector(state => state.toewijzing.entities);
  const voorzienings = useAppSelector(state => state.voorziening.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const leveringEntity = useAppSelector(state => state.levering.entity);
  const loading = useAppSelector(state => state.levering.loading);
  const updating = useAppSelector(state => state.levering.updating);
  const updateSuccess = useAppSelector(state => state.levering.updateSuccess);

  const handleClose = () => {
    navigate('/levering');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBeschikkings({}));
    dispatch(getClients({}));
    dispatch(getToewijzings({}));
    dispatch(getVoorzienings({}));
    dispatch(getLeveranciers({}));
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
      ...leveringEntity,
      ...values,
      geleverdeprestatieBeschikking: beschikkings.find(it => it.id.toString() === values.geleverdeprestatieBeschikking?.toString()),
      prestatievoorClient: clients.find(it => it.id.toString() === values.prestatievoorClient?.toString()),
      geleverdezorgToewijzing: toewijzings.find(it => it.id.toString() === values.geleverdezorgToewijzing?.toString()),
      voorzieningVoorziening: voorzienings.find(it => it.id.toString() === values.voorzieningVoorziening?.toString()),
      leverdeprestatieLeverancier: leveranciers.find(it => it.id.toString() === values.leverdeprestatieLeverancier?.toString()),
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
          ...leveringEntity,
          geleverdeprestatieBeschikking: leveringEntity?.geleverdeprestatieBeschikking?.id,
          prestatievoorClient: leveringEntity?.prestatievoorClient?.id,
          geleverdezorgToewijzing: leveringEntity?.geleverdezorgToewijzing?.id,
          voorzieningVoorziening: leveringEntity?.voorzieningVoorziening?.id,
          leverdeprestatieLeverancier: leveringEntity?.leverdeprestatieLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.levering.home.createOrEditLabel" data-cy="LeveringCreateUpdateHeading">
            Create or edit a Levering
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="levering-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Code"
                id="levering-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Datumstart" id="levering-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Datumstop" id="levering-datumstop" name="datumstop" data-cy="datumstop" type="date" />
              <ValidatedField label="Eenheid" id="levering-eenheid" name="eenheid" data-cy="eenheid" type="text" />
              <ValidatedField label="Frequentie" id="levering-frequentie" name="frequentie" data-cy="frequentie" type="text" />
              <ValidatedField label="Omvang" id="levering-omvang" name="omvang" data-cy="omvang" type="text" />
              <ValidatedField label="Stopreden" id="levering-stopreden" name="stopreden" data-cy="stopreden" type="text" />
              <ValidatedField
                id="levering-geleverdeprestatieBeschikking"
                name="geleverdeprestatieBeschikking"
                data-cy="geleverdeprestatieBeschikking"
                label="Geleverdeprestatie Beschikking"
                type="select"
              >
                <option value="" key="0" />
                {beschikkings
                  ? beschikkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="levering-prestatievoorClient"
                name="prestatievoorClient"
                data-cy="prestatievoorClient"
                label="Prestatievoor Client"
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="levering-geleverdezorgToewijzing"
                name="geleverdezorgToewijzing"
                data-cy="geleverdezorgToewijzing"
                label="Geleverdezorg Toewijzing"
                type="select"
              >
                <option value="" key="0" />
                {toewijzings
                  ? toewijzings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="levering-voorzieningVoorziening"
                name="voorzieningVoorziening"
                data-cy="voorzieningVoorziening"
                label="Voorziening Voorziening"
                type="select"
              >
                <option value="" key="0" />
                {voorzienings
                  ? voorzienings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="levering-leverdeprestatieLeverancier"
                name="leverdeprestatieLeverancier"
                data-cy="leverdeprestatieLeverancier"
                label="Leverdeprestatie Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/levering" replace color="info">
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

export default LeveringUpdate;
