import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPand } from 'app/shared/model/pand.model';
import { getEntities as getPands } from 'app/entities/pand/pand.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { getEntity, updateEntity, createEntity, reset } from './vastgoedobject.reducer';

export const VastgoedobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pands = useAppSelector(state => state.pand.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const vastgoedobjectEntity = useAppSelector(state => state.vastgoedobject.entity);
  const loading = useAppSelector(state => state.vastgoedobject.loading);
  const updating = useAppSelector(state => state.vastgoedobject.updating);
  const updateSuccess = useAppSelector(state => state.vastgoedobject.updateSuccess);

  const handleClose = () => {
    navigate('/vastgoedobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPands({}));
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
    if (values.afkoopwaarde !== undefined && typeof values.afkoopwaarde !== 'number') {
      values.afkoopwaarde = Number(values.afkoopwaarde);
    }
    if (values.bedragaankoop !== undefined && typeof values.bedragaankoop !== 'number') {
      values.bedragaankoop = Number(values.bedragaankoop);
    }
    if (values.boekwaarde !== undefined && typeof values.boekwaarde !== 'number') {
      values.boekwaarde = Number(values.boekwaarde);
    }
    if (values.energiekosten !== undefined && typeof values.energiekosten !== 'number') {
      values.energiekosten = Number(values.energiekosten);
    }
    if (values.fiscalewaarde !== undefined && typeof values.fiscalewaarde !== 'number') {
      values.fiscalewaarde = Number(values.fiscalewaarde);
    }
    if (values.herbouwwaarde !== undefined && typeof values.herbouwwaarde !== 'number') {
      values.herbouwwaarde = Number(values.herbouwwaarde);
    }
    if (values.marktwaarde !== undefined && typeof values.marktwaarde !== 'number') {
      values.marktwaarde = Number(values.marktwaarde);
    }
    if (values.verkoopbedrag !== undefined && typeof values.verkoopbedrag !== 'number') {
      values.verkoopbedrag = Number(values.verkoopbedrag);
    }
    if (values.verzekerdewaarde !== undefined && typeof values.verzekerdewaarde !== 'number') {
      values.verzekerdewaarde = Number(values.verzekerdewaarde);
    }
    if (values.waardegrond !== undefined && typeof values.waardegrond !== 'number') {
      values.waardegrond = Number(values.waardegrond);
    }
    if (values.waardeopstal !== undefined && typeof values.waardeopstal !== 'number') {
      values.waardeopstal = Number(values.waardeopstal);
    }
    if (values.wozwaarde !== undefined && typeof values.wozwaarde !== 'number') {
      values.wozwaarde = Number(values.wozwaarde);
    }

    const entity = {
      ...vastgoedobjectEntity,
      ...values,
      betreftPand: pands.find(it => it.id.toString() === values.betreftPand?.toString()),
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
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
          ...vastgoedobjectEntity,
          betreftPand: vastgoedobjectEntity?.betreftPand?.id,
          heeftKostenplaats: vastgoedobjectEntity?.heeftKostenplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vastgoedobject.home.createOrEditLabel" data-cy="VastgoedobjectCreateUpdateHeading">
            Create or edit a Vastgoedobject
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
                <ValidatedField name="id" required readOnly id="vastgoedobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantaletages"
                id="vastgoedobject-aantaletages"
                name="aantaletages"
                data-cy="aantaletages"
                type="text"
              />
              <ValidatedField
                label="Aantalparkeerplaatsen"
                id="vastgoedobject-aantalparkeerplaatsen"
                name="aantalparkeerplaatsen"
                data-cy="aantalparkeerplaatsen"
                type="text"
              />
              <ValidatedField
                label="Aantalrioleringen"
                id="vastgoedobject-aantalrioleringen"
                name="aantalrioleringen"
                data-cy="aantalrioleringen"
                type="text"
              />
              <ValidatedField
                label="Adresaanduiding"
                id="vastgoedobject-adresaanduiding"
                name="adresaanduiding"
                data-cy="adresaanduiding"
                type="text"
              />
              <ValidatedField
                label="Afgekochteerfpacht"
                id="vastgoedobject-afgekochteerfpacht"
                name="afgekochteerfpacht"
                data-cy="afgekochteerfpacht"
                type="text"
              />
              <ValidatedField
                label="Afgesprokenconditiescore"
                id="vastgoedobject-afgesprokenconditiescore"
                name="afgesprokenconditiescore"
                data-cy="afgesprokenconditiescore"
                type="text"
              />
              <ValidatedField
                label="Afkoopwaarde"
                id="vastgoedobject-afkoopwaarde"
                name="afkoopwaarde"
                data-cy="afkoopwaarde"
                type="text"
              />
              <ValidatedField
                label="Asbestrapportageaanwezig"
                id="vastgoedobject-asbestrapportageaanwezig"
                name="asbestrapportageaanwezig"
                data-cy="asbestrapportageaanwezig"
                type="text"
              />
              <ValidatedField
                label="Bedragaankoop"
                id="vastgoedobject-bedragaankoop"
                name="bedragaankoop"
                data-cy="bedragaankoop"
                type="text"
              />
              <ValidatedField
                label="Bestemmingsplan"
                id="vastgoedobject-bestemmingsplan"
                name="bestemmingsplan"
                data-cy="bestemmingsplan"
                type="text"
              />
              <ValidatedField label="Boekwaarde" id="vastgoedobject-boekwaarde" name="boekwaarde" data-cy="boekwaarde" type="text" />
              <ValidatedField label="Bouwjaar" id="vastgoedobject-bouwjaar" name="bouwjaar" data-cy="bouwjaar" type="text" />
              <ValidatedField label="Bouwwerk" id="vastgoedobject-bouwwerk" name="bouwwerk" data-cy="bouwwerk" type="text" />
              <ValidatedField
                label="Bovenliggendniveau"
                id="vastgoedobject-bovenliggendniveau"
                name="bovenliggendniveau"
                data-cy="bovenliggendniveau"
                type="text"
              />
              <ValidatedField
                label="Bovenliggendniveaucode"
                id="vastgoedobject-bovenliggendniveaucode"
                name="bovenliggendniveaucode"
                data-cy="bovenliggendniveaucode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Brutovloeroppervlakte"
                id="vastgoedobject-brutovloeroppervlakte"
                name="brutovloeroppervlakte"
                data-cy="brutovloeroppervlakte"
                type="text"
              />
              <ValidatedField label="Co 2 Uitstoot" id="vastgoedobject-co2uitstoot" name="co2uitstoot" data-cy="co2uitstoot" type="text" />
              <ValidatedField
                label="Conditiescore"
                id="vastgoedobject-conditiescore"
                name="conditiescore"
                data-cy="conditiescore"
                type="text"
              />
              <ValidatedField
                label="Datumafstoten"
                id="vastgoedobject-datumafstoten"
                name="datumafstoten"
                data-cy="datumafstoten"
                type="date"
              />
              <ValidatedField
                label="Datumberekeningoppervlak"
                id="vastgoedobject-datumberekeningoppervlak"
                name="datumberekeningoppervlak"
                data-cy="datumberekeningoppervlak"
                type="date"
              />
              <ValidatedField
                label="Datumeigendom"
                id="vastgoedobject-datumeigendom"
                name="datumeigendom"
                data-cy="datumeigendom"
                type="date"
              />
              <ValidatedField
                label="Datumverkoop"
                id="vastgoedobject-datumverkoop"
                name="datumverkoop"
                data-cy="datumverkoop"
                type="date"
              />
              <ValidatedField
                label="Deelportefeuille"
                id="vastgoedobject-deelportefeuille"
                name="deelportefeuille"
                data-cy="deelportefeuille"
                type="text"
              />
              <ValidatedField
                label="Energiekosten"
                id="vastgoedobject-energiekosten"
                name="energiekosten"
                data-cy="energiekosten"
                type="text"
              />
              <ValidatedField
                label="Energielabel"
                id="vastgoedobject-energielabel"
                name="energielabel"
                data-cy="energielabel"
                type="text"
              />
              <ValidatedField
                label="Energieverbruik"
                id="vastgoedobject-energieverbruik"
                name="energieverbruik"
                data-cy="energieverbruik"
                type="text"
              />
              <ValidatedField
                label="Fiscalewaarde"
                id="vastgoedobject-fiscalewaarde"
                name="fiscalewaarde"
                data-cy="fiscalewaarde"
                type="text"
              />
              <ValidatedField label="Foto" id="vastgoedobject-foto" name="foto" data-cy="foto" type="text" />
              <ValidatedField
                label="Gearchiveerd"
                id="vastgoedobject-gearchiveerd"
                name="gearchiveerd"
                data-cy="gearchiveerd"
                type="text"
              />
              <ValidatedField
                label="Herbouwwaarde"
                id="vastgoedobject-herbouwwaarde"
                name="herbouwwaarde"
                data-cy="herbouwwaarde"
                type="text"
              />
              <ValidatedField label="Hoofdstuk" id="vastgoedobject-hoofdstuk" name="hoofdstuk" data-cy="hoofdstuk" type="text" />
              <ValidatedField
                label="Identificatie"
                id="vastgoedobject-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Jaarlaatsterenovatie"
                id="vastgoedobject-jaarlaatsterenovatie"
                name="jaarlaatsterenovatie"
                data-cy="jaarlaatsterenovatie"
                type="text"
              />
              <ValidatedField label="Locatie" id="vastgoedobject-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Marktwaarde" id="vastgoedobject-marktwaarde" name="marktwaarde" data-cy="marktwaarde" type="text" />
              <ValidatedField label="Monument" id="vastgoedobject-monument" name="monument" data-cy="monument" type="text" />
              <ValidatedField label="Naam" id="vastgoedobject-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Eobjectstatus"
                id="vastgoedobject-eobjectstatus"
                name="eobjectstatus"
                data-cy="eobjectstatus"
                type="text"
              />
              <ValidatedField
                label="Eobjectstatuscode"
                id="vastgoedobject-eobjectstatuscode"
                name="eobjectstatuscode"
                data-cy="eobjectstatuscode"
                type="text"
              />
              <ValidatedField label="Eobjecttype" id="vastgoedobject-eobjecttype" name="eobjecttype" data-cy="eobjecttype" type="text" />
              <ValidatedField
                label="Eobjecttypecode"
                id="vastgoedobject-eobjecttypecode"
                name="eobjecttypecode"
                data-cy="eobjecttypecode"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="vastgoedobject-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Onderhoudscategorie"
                id="vastgoedobject-onderhoudscategorie"
                name="onderhoudscategorie"
                data-cy="onderhoudscategorie"
                type="text"
              />
              <ValidatedField
                label="Oppervlaktekantoor"
                id="vastgoedobject-oppervlaktekantoor"
                name="oppervlaktekantoor"
                data-cy="oppervlaktekantoor"
                type="text"
              />
              <ValidatedField
                label="Portefeuille"
                id="vastgoedobject-portefeuille"
                name="portefeuille"
                data-cy="portefeuille"
                type="text"
              />
              <ValidatedField
                label="Portefeuillecode"
                id="vastgoedobject-portefeuillecode"
                name="portefeuillecode"
                data-cy="portefeuillecode"
                type="text"
              />
              <ValidatedField label="Provincie" id="vastgoedobject-provincie" name="provincie" data-cy="provincie" type="text" />
              <ValidatedField label="Toelichting" id="vastgoedobject-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Verhuurbaarvloeroppervlak"
                id="vastgoedobject-verhuurbaarvloeroppervlak"
                name="verhuurbaarvloeroppervlak"
                data-cy="verhuurbaarvloeroppervlak"
                type="text"
              />
              <ValidatedField
                label="Verkoopbaarheid"
                id="vastgoedobject-verkoopbaarheid"
                name="verkoopbaarheid"
                data-cy="verkoopbaarheid"
                type="text"
              />
              <ValidatedField
                label="Verkoopbedrag"
                id="vastgoedobject-verkoopbedrag"
                name="verkoopbedrag"
                data-cy="verkoopbedrag"
                type="text"
              />
              <ValidatedField
                label="Verzekerdewaarde"
                id="vastgoedobject-verzekerdewaarde"
                name="verzekerdewaarde"
                data-cy="verzekerdewaarde"
                type="text"
              />
              <ValidatedField label="Waardegrond" id="vastgoedobject-waardegrond" name="waardegrond" data-cy="waardegrond" type="text" />
              <ValidatedField
                label="Waardeopstal"
                id="vastgoedobject-waardeopstal"
                name="waardeopstal"
                data-cy="waardeopstal"
                type="text"
              />
              <ValidatedField label="Wijk" id="vastgoedobject-wijk" name="wijk" data-cy="wijk" type="text" />
              <ValidatedField label="Wozwaarde" id="vastgoedobject-wozwaarde" name="wozwaarde" data-cy="wozwaarde" type="text" />
              <ValidatedField id="vastgoedobject-betreftPand" name="betreftPand" data-cy="betreftPand" label="Betreft Pand" type="select">
                <option value="" key="0" />
                {pands
                  ? pands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vastgoedobject-heeftKostenplaats"
                name="heeftKostenplaats"
                data-cy="heeftKostenplaats"
                label="Heeft Kostenplaats"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vastgoedobject" replace color="info">
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

export default VastgoedobjectUpdate;
