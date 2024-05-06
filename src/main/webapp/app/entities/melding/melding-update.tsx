import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { IContainertype } from 'app/shared/model/containertype.model';
import { getEntities as getContainertypes } from 'app/entities/containertype/containertype.reducer';
import { IFractie } from 'app/shared/model/fractie.model';
import { getEntities as getFracties } from 'app/entities/fractie/fractie.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IBeheerobject } from 'app/shared/model/beheerobject.model';
import { getEntities as getBeheerobjects } from 'app/entities/beheerobject/beheerobject.reducer';
import { ISchouwronde } from 'app/shared/model/schouwronde.model';
import { getEntities as getSchouwrondes } from 'app/entities/schouwronde/schouwronde.reducer';
import { IMelding } from 'app/shared/model/melding.model';
import { getEntity, updateEntity, createEntity, reset } from './melding.reducer';

export const MeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.categorie.entities);
  const containertypes = useAppSelector(state => state.containertype.entities);
  const fracties = useAppSelector(state => state.fractie.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const beheerobjects = useAppSelector(state => state.beheerobject.entities);
  const schouwrondes = useAppSelector(state => state.schouwronde.entities);
  const meldingEntity = useAppSelector(state => state.melding.entity);
  const loading = useAppSelector(state => state.melding.loading);
  const updating = useAppSelector(state => state.melding.updating);
  const updateSuccess = useAppSelector(state => state.melding.updateSuccess);

  const handleClose = () => {
    navigate('/melding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
    dispatch(getContainertypes({}));
    dispatch(getFracties({}));
    dispatch(getLocaties({}));
    dispatch(getMedewerkers({}));
    dispatch(getLeveranciers({}));
    dispatch(getBeheerobjects({}));
    dispatch(getSchouwrondes({}));
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
      ...meldingEntity,
      ...values,
      hoofdcategorieCategorie: categories.find(it => it.id.toString() === values.hoofdcategorieCategorie?.toString()),
      subcategorieCategorie: categories.find(it => it.id.toString() === values.subcategorieCategorie?.toString()),
      betreftContainertype: containertypes.find(it => it.id.toString() === values.betreftContainertype?.toString()),
      betreftFractie: fracties.find(it => it.id.toString() === values.betreftFractie?.toString()),
      betreftLocatie: locaties.find(it => it.id.toString() === values.betreftLocatie?.toString()),
      melderMedewerker: medewerkers.find(it => it.id.toString() === values.melderMedewerker?.toString()),
      uitvoerderLeverancier: leveranciers.find(it => it.id.toString() === values.uitvoerderLeverancier?.toString()),
      uitvoerderMedewerker: medewerkers.find(it => it.id.toString() === values.uitvoerderMedewerker?.toString()),
      betreftBeheerobjects: mapIdList(values.betreftBeheerobjects),
      heeftSchouwronde: schouwrondes.find(it => it.id.toString() === values.heeftSchouwronde?.toString()),
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
          ...meldingEntity,
          hoofdcategorieCategorie: meldingEntity?.hoofdcategorieCategorie?.id,
          subcategorieCategorie: meldingEntity?.subcategorieCategorie?.id,
          betreftContainertype: meldingEntity?.betreftContainertype?.id,
          betreftFractie: meldingEntity?.betreftFractie?.id,
          betreftLocatie: meldingEntity?.betreftLocatie?.id,
          melderMedewerker: meldingEntity?.melderMedewerker?.id,
          uitvoerderLeverancier: meldingEntity?.uitvoerderLeverancier?.id,
          uitvoerderMedewerker: meldingEntity?.uitvoerderMedewerker?.id,
          betreftBeheerobjects: meldingEntity?.betreftBeheerobjects?.map(e => e.id.toString()),
          heeftSchouwronde: meldingEntity?.heeftSchouwronde?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.melding.home.createOrEditLabel" data-cy="MeldingCreateUpdateHeading">
            Create or edit a Melding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="melding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Vierentwintiguurs"
                id="melding-vierentwintiguurs"
                name="vierentwintiguurs"
                data-cy="vierentwintiguurs"
                type="text"
              />
              <ValidatedField label="Datumtijd" id="melding-datumtijd" name="datumtijd" data-cy="datumtijd" type="text" />
              <ValidatedField label="Illegaal" id="melding-illegaal" name="illegaal" data-cy="illegaal" type="text" />
              <ValidatedField label="Meldingnummer" id="melding-meldingnummer" name="meldingnummer" data-cy="meldingnummer" type="text" />
              <ValidatedField label="Omschrijving" id="melding-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="melding-hoofdcategorieCategorie"
                name="hoofdcategorieCategorie"
                data-cy="hoofdcategorieCategorie"
                label="Hoofdcategorie Categorie"
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-subcategorieCategorie"
                name="subcategorieCategorie"
                data-cy="subcategorieCategorie"
                label="Subcategorie Categorie"
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-betreftContainertype"
                name="betreftContainertype"
                data-cy="betreftContainertype"
                label="Betreft Containertype"
                type="select"
              >
                <option value="" key="0" />
                {containertypes
                  ? containertypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-betreftFractie"
                name="betreftFractie"
                data-cy="betreftFractie"
                label="Betreft Fractie"
                type="select"
              >
                <option value="" key="0" />
                {fracties
                  ? fracties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-betreftLocatie"
                name="betreftLocatie"
                data-cy="betreftLocatie"
                label="Betreft Locatie"
                type="select"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-melderMedewerker"
                name="melderMedewerker"
                data-cy="melderMedewerker"
                label="Melder Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-uitvoerderLeverancier"
                name="uitvoerderLeverancier"
                data-cy="uitvoerderLeverancier"
                label="Uitvoerder Leverancier"
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
              <ValidatedField
                id="melding-uitvoerderMedewerker"
                name="uitvoerderMedewerker"
                data-cy="uitvoerderMedewerker"
                label="Uitvoerder Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Beheerobject"
                id="melding-betreftBeheerobject"
                data-cy="betreftBeheerobject"
                type="select"
                multiple
                name="betreftBeheerobjects"
              >
                <option value="" key="0" />
                {beheerobjects
                  ? beheerobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="melding-heeftSchouwronde"
                name="heeftSchouwronde"
                data-cy="heeftSchouwronde"
                label="Heeft Schouwronde"
                type="select"
              >
                <option value="" key="0" />
                {schouwrondes
                  ? schouwrondes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/melding" replace color="info">
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

export default MeldingUpdate;
