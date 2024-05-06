import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParticipatiedossier } from 'app/shared/model/participatiedossier.model';
import { getEntities as getParticipatiedossiers } from 'app/entities/participatiedossier/participatiedossier.reducer';
import { IInkomensvoorziening } from 'app/shared/model/inkomensvoorziening.model';
import { getEntities as getInkomensvoorzienings } from 'app/entities/inkomensvoorziening/inkomensvoorziening.reducer';
import { IDoelgroep } from 'app/shared/model/doelgroep.model';
import { getEntities as getDoelgroeps } from 'app/entities/doelgroep/doelgroep.reducer';
import { IRelatie } from 'app/shared/model/relatie.model';
import { getEntities as getRelaties } from 'app/entities/relatie/relatie.reducer';
import { IHuishouden } from 'app/shared/model/huishouden.model';
import { getEntities as getHuishoudens } from 'app/entities/huishouden/huishouden.reducer';
import { ITaalniveau } from 'app/shared/model/taalniveau.model';
import { getEntities as getTaalniveaus } from 'app/entities/taalniveau/taalniveau.reducer';
import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { getEntities as getClientbegeleiders } from 'app/entities/clientbegeleider/clientbegeleider.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';

export const ClientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const participatiedossiers = useAppSelector(state => state.participatiedossier.entities);
  const inkomensvoorzienings = useAppSelector(state => state.inkomensvoorziening.entities);
  const doelgroeps = useAppSelector(state => state.doelgroep.entities);
  const relaties = useAppSelector(state => state.relatie.entities);
  const huishoudens = useAppSelector(state => state.huishouden.entities);
  const taalniveaus = useAppSelector(state => state.taalniveau.entities);
  const clientbegeleiders = useAppSelector(state => state.clientbegeleider.entities);
  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);
  const updating = useAppSelector(state => state.client.updating);
  const updateSuccess = useAppSelector(state => state.client.updateSuccess);

  const handleClose = () => {
    navigate('/client');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getParticipatiedossiers({}));
    dispatch(getInkomensvoorzienings({}));
    dispatch(getDoelgroeps({}));
    dispatch(getRelaties({}));
    dispatch(getHuishoudens({}));
    dispatch(getTaalniveaus({}));
    dispatch(getClientbegeleiders({}));
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
      ...clientEntity,
      ...values,
      heeftParticipatiedossier: participatiedossiers.find(it => it.id.toString() === values.heeftParticipatiedossier?.toString()),
      heeftvoorzieningInkomensvoorziening: inkomensvoorzienings.find(
        it => it.id.toString() === values.heeftvoorzieningInkomensvoorziening?.toString(),
      ),
      valtbinnendoelgroepDoelgroep: doelgroeps.find(it => it.id.toString() === values.valtbinnendoelgroepDoelgroep?.toString()),
      heeftrelatieRelaties: mapIdList(values.heeftrelatieRelaties),
      voorzieningbijstandspartijInkomensvoorzienings: mapIdList(values.voorzieningbijstandspartijInkomensvoorzienings),
      maaktonderdeeluitvanHuishoudens: mapIdList(values.maaktonderdeeluitvanHuishoudens),
      heefttaalniveauTaalniveaus: mapIdList(values.heefttaalniveauTaalniveaus),
      ondersteuntclientClientbegeleiders: mapIdList(values.ondersteuntclientClientbegeleiders),
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
          ...clientEntity,
          heeftParticipatiedossier: clientEntity?.heeftParticipatiedossier?.id,
          heeftvoorzieningInkomensvoorziening: clientEntity?.heeftvoorzieningInkomensvoorziening?.id,
          valtbinnendoelgroepDoelgroep: clientEntity?.valtbinnendoelgroepDoelgroep?.id,
          heeftrelatieRelaties: clientEntity?.heeftrelatieRelaties?.map(e => e.id.toString()),
          voorzieningbijstandspartijInkomensvoorzienings: clientEntity?.voorzieningbijstandspartijInkomensvoorzienings?.map(e =>
            e.id.toString(),
          ),
          maaktonderdeeluitvanHuishoudens: clientEntity?.maaktonderdeeluitvanHuishoudens?.map(e => e.id.toString()),
          heefttaalniveauTaalniveaus: clientEntity?.heefttaalniveauTaalniveaus?.map(e => e.id.toString()),
          ondersteuntclientClientbegeleiders: clientEntity?.ondersteuntclientClientbegeleiders?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            Create or edit a Client
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="client-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="client-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Gezagsdragergekend"
                id="client-gezagsdragergekend"
                name="gezagsdragergekend"
                data-cy="gezagsdragergekend"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Juridischestatus"
                id="client-juridischestatus"
                name="juridischestatus"
                data-cy="juridischestatus"
                type="text"
              />
              <ValidatedField
                label="Wettelijkevertegenwoordiging"
                id="client-wettelijkevertegenwoordiging"
                name="wettelijkevertegenwoordiging"
                data-cy="wettelijkevertegenwoordiging"
                type="text"
              />
              <ValidatedField
                id="client-heeftParticipatiedossier"
                name="heeftParticipatiedossier"
                data-cy="heeftParticipatiedossier"
                label="Heeft Participatiedossier"
                type="select"
                required
              >
                <option value="" key="0" />
                {participatiedossiers
                  ? participatiedossiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="client-heeftvoorzieningInkomensvoorziening"
                name="heeftvoorzieningInkomensvoorziening"
                data-cy="heeftvoorzieningInkomensvoorziening"
                label="Heeftvoorziening Inkomensvoorziening"
                type="select"
                required
              >
                <option value="" key="0" />
                {inkomensvoorzienings
                  ? inkomensvoorzienings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="client-valtbinnendoelgroepDoelgroep"
                name="valtbinnendoelgroepDoelgroep"
                data-cy="valtbinnendoelgroepDoelgroep"
                label="Valtbinnendoelgroep Doelgroep"
                type="select"
              >
                <option value="" key="0" />
                {doelgroeps
                  ? doelgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftrelatie Relatie"
                id="client-heeftrelatieRelatie"
                data-cy="heeftrelatieRelatie"
                type="select"
                multiple
                name="heeftrelatieRelaties"
              >
                <option value="" key="0" />
                {relaties
                  ? relaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Voorzieningbijstandspartij Inkomensvoorziening"
                id="client-voorzieningbijstandspartijInkomensvoorziening"
                data-cy="voorzieningbijstandspartijInkomensvoorziening"
                type="select"
                multiple
                name="voorzieningbijstandspartijInkomensvoorzienings"
              >
                <option value="" key="0" />
                {inkomensvoorzienings
                  ? inkomensvoorzienings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Maaktonderdeeluitvan Huishouden"
                id="client-maaktonderdeeluitvanHuishouden"
                data-cy="maaktonderdeeluitvanHuishouden"
                type="select"
                multiple
                name="maaktonderdeeluitvanHuishoudens"
              >
                <option value="" key="0" />
                {huishoudens
                  ? huishoudens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heefttaalniveau Taalniveau"
                id="client-heefttaalniveauTaalniveau"
                data-cy="heefttaalniveauTaalniveau"
                type="select"
                multiple
                name="heefttaalniveauTaalniveaus"
              >
                <option value="" key="0" />
                {taalniveaus
                  ? taalniveaus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Ondersteuntclient Clientbegeleider"
                id="client-ondersteuntclientClientbegeleider"
                data-cy="ondersteuntclientClientbegeleider"
                type="select"
                multiple
                name="ondersteuntclientClientbegeleiders"
              >
                <option value="" key="0" />
                {clientbegeleiders
                  ? clientbegeleiders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client" replace color="info">
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

export default ClientUpdate;
