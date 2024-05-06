import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITaakveld } from 'app/shared/model/taakveld.model';
import { getEntities as getTaakvelds } from 'app/entities/taakveld/taakveld.reducer';
import { IAgendapunt } from 'app/shared/model/agendapunt.model';
import { getEntities as getAgendapunts } from 'app/entities/agendapunt/agendapunt.reducer';
import { IProgramma } from 'app/shared/model/programma.model';
import { getEntities as getProgrammas } from 'app/entities/programma/programma.reducer';
import { IVergadering } from 'app/shared/model/vergadering.model';
import { getEntities as getVergaderings } from 'app/entities/vergadering/vergadering.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { IDossier } from 'app/shared/model/dossier.model';
import { getEntities as getDossiers } from 'app/entities/dossier/dossier.reducer';
import { IIndiener } from 'app/shared/model/indiener.model';
import { getEntities as getIndieners } from 'app/entities/indiener/indiener.reducer';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntity, updateEntity, createEntity, reset } from './raadsstuk.reducer';

export const RaadsstukUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const taakvelds = useAppSelector(state => state.taakveld.entities);
  const agendapunts = useAppSelector(state => state.agendapunt.entities);
  const programmas = useAppSelector(state => state.programma.entities);
  const vergaderings = useAppSelector(state => state.vergadering.entities);
  const categories = useAppSelector(state => state.categorie.entities);
  const dossiers = useAppSelector(state => state.dossier.entities);
  const indieners = useAppSelector(state => state.indiener.entities);
  const raadsstukEntity = useAppSelector(state => state.raadsstuk.entity);
  const loading = useAppSelector(state => state.raadsstuk.loading);
  const updating = useAppSelector(state => state.raadsstuk.updating);
  const updateSuccess = useAppSelector(state => state.raadsstuk.updateSuccess);

  const handleClose = () => {
    navigate('/raadsstuk');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTaakvelds({}));
    dispatch(getAgendapunts({}));
    dispatch(getProgrammas({}));
    dispatch(getVergaderings({}));
    dispatch(getCategories({}));
    dispatch(getDossiers({}));
    dispatch(getIndieners({}));
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
      ...raadsstukEntity,
      ...values,
      heeftTaakveld: taakvelds.find(it => it.id.toString() === values.heeftTaakveld?.toString()),
      behandeltAgendapunts: mapIdList(values.behandeltAgendapunts),
      hoortbijProgrammas: mapIdList(values.hoortbijProgrammas),
      wordtbehandeldinVergaderings: mapIdList(values.wordtbehandeldinVergaderings),
      heeftCategorie: categories.find(it => it.id.toString() === values.heeftCategorie?.toString()),
      hoortbijDossiers: mapIdList(values.hoortbijDossiers),
      heeftIndieners: mapIdList(values.heeftIndieners),
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
          ...raadsstukEntity,
          heeftTaakveld: raadsstukEntity?.heeftTaakveld?.id,
          behandeltAgendapunts: raadsstukEntity?.behandeltAgendapunts?.map(e => e.id.toString()),
          hoortbijProgrammas: raadsstukEntity?.hoortbijProgrammas?.map(e => e.id.toString()),
          wordtbehandeldinVergaderings: raadsstukEntity?.wordtbehandeldinVergaderings?.map(e => e.id.toString()),
          heeftCategorie: raadsstukEntity?.heeftCategorie?.id,
          hoortbijDossiers: raadsstukEntity?.hoortbijDossiers?.map(e => e.id.toString()),
          heeftIndieners: raadsstukEntity?.heeftIndieners?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.raadsstuk.home.createOrEditLabel" data-cy="RaadsstukCreateUpdateHeading">
            Create or edit a Raadsstuk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="raadsstuk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Besloten" id="raadsstuk-besloten" name="besloten" data-cy="besloten" type="text" />
              <ValidatedField
                label="Datumexpiratie"
                id="raadsstuk-datumexpiratie"
                name="datumexpiratie"
                data-cy="datumexpiratie"
                type="text"
              />
              <ValidatedField
                label="Datumpublicatie"
                id="raadsstuk-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField
                label="Datumregistratie"
                id="raadsstuk-datumregistratie"
                name="datumregistratie"
                data-cy="datumregistratie"
                type="text"
              />
              <ValidatedField label="Typeraadsstuk" id="raadsstuk-typeraadsstuk" name="typeraadsstuk" data-cy="typeraadsstuk" type="text" />
              <ValidatedField
                id="raadsstuk-heeftTaakveld"
                name="heeftTaakveld"
                data-cy="heeftTaakveld"
                label="Heeft Taakveld"
                type="select"
              >
                <option value="" key="0" />
                {taakvelds
                  ? taakvelds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Behandelt Agendapunt"
                id="raadsstuk-behandeltAgendapunt"
                data-cy="behandeltAgendapunt"
                type="select"
                multiple
                name="behandeltAgendapunts"
              >
                <option value="" key="0" />
                {agendapunts
                  ? agendapunts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Hoortbij Programma"
                id="raadsstuk-hoortbijProgramma"
                data-cy="hoortbijProgramma"
                type="select"
                multiple
                name="hoortbijProgrammas"
              >
                <option value="" key="0" />
                {programmas
                  ? programmas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Wordtbehandeldin Vergadering"
                id="raadsstuk-wordtbehandeldinVergadering"
                data-cy="wordtbehandeldinVergadering"
                type="select"
                multiple
                name="wordtbehandeldinVergaderings"
              >
                <option value="" key="0" />
                {vergaderings
                  ? vergaderings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="raadsstuk-heeftCategorie"
                name="heeftCategorie"
                data-cy="heeftCategorie"
                label="Heeft Categorie"
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
                label="Hoortbij Dossier"
                id="raadsstuk-hoortbijDossier"
                data-cy="hoortbijDossier"
                type="select"
                multiple
                name="hoortbijDossiers"
              >
                <option value="" key="0" />
                {dossiers
                  ? dossiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Indiener"
                id="raadsstuk-heeftIndiener"
                data-cy="heeftIndiener"
                type="select"
                multiple
                name="heeftIndieners"
              >
                <option value="" key="0" />
                {indieners
                  ? indieners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/raadsstuk" replace color="info">
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

export default RaadsstukUpdate;
