import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPut } from 'app/shared/model/put.model';
import { getEntities as getPuts } from 'app/entities/put/put.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { IRegeltekst } from 'app/shared/model/regeltekst.model';
import { getEntities as getRegelteksts } from 'app/entities/regeltekst/regeltekst.reducer';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { getEntities as getActiviteits } from 'app/entities/activiteit/activiteit.reducer';
import { IGebiedsaanwijzing } from 'app/shared/model/gebiedsaanwijzing.model';
import { getEntities as getGebiedsaanwijzings } from 'app/entities/gebiedsaanwijzing/gebiedsaanwijzing.reducer';
import { INormwaarde } from 'app/shared/model/normwaarde.model';
import { getEntities as getNormwaardes } from 'app/entities/normwaarde/normwaarde.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntity, updateEntity, createEntity, reset } from './locatie.reducer';

export const LocatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const puts = useAppSelector(state => state.put.entities);
  const projects = useAppSelector(state => state.project.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const regelteksts = useAppSelector(state => state.regeltekst.entities);
  const activiteits = useAppSelector(state => state.activiteit.entities);
  const gebiedsaanwijzings = useAppSelector(state => state.gebiedsaanwijzing.entities);
  const normwaardes = useAppSelector(state => state.normwaarde.entities);
  const locatieEntity = useAppSelector(state => state.locatie.entity);
  const loading = useAppSelector(state => state.locatie.loading);
  const updating = useAppSelector(state => state.locatie.updating);
  const updateSuccess = useAppSelector(state => state.locatie.updateSuccess);

  const handleClose = () => {
    navigate('/locatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPuts({}));
    dispatch(getProjects({}));
    dispatch(getVerzoeks({}));
    dispatch(getRegelteksts({}));
    dispatch(getActiviteits({}));
    dispatch(getGebiedsaanwijzings({}));
    dispatch(getNormwaardes({}));
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
      ...locatieEntity,
      ...values,
      heeftlocatiePuts: mapIdList(values.heeftlocatiePuts),
      wordtbegrensddoorProjects: mapIdList(values.wordtbegrensddoorProjects),
      betreftVerzoeks: mapIdList(values.betreftVerzoeks),
      werkingsgebiedRegelteksts: mapIdList(values.werkingsgebiedRegelteksts),
      isverbondenmetActiviteits: mapIdList(values.isverbondenmetActiviteits),
      verwijstnaarGebiedsaanwijzings: mapIdList(values.verwijstnaarGebiedsaanwijzings),
      geldtvoorNormwaardes: mapIdList(values.geldtvoorNormwaardes),
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
          ...locatieEntity,
          heeftlocatiePuts: locatieEntity?.heeftlocatiePuts?.map(e => e.id.toString()),
          wordtbegrensddoorProjects: locatieEntity?.wordtbegrensddoorProjects?.map(e => e.id.toString()),
          betreftVerzoeks: locatieEntity?.betreftVerzoeks?.map(e => e.id.toString()),
          werkingsgebiedRegelteksts: locatieEntity?.werkingsgebiedRegelteksts?.map(e => e.id.toString()),
          isverbondenmetActiviteits: locatieEntity?.isverbondenmetActiviteits?.map(e => e.id.toString()),
          verwijstnaarGebiedsaanwijzings: locatieEntity?.verwijstnaarGebiedsaanwijzings?.map(e => e.id.toString()),
          geldtvoorNormwaardes: locatieEntity?.geldtvoorNormwaardes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.locatie.home.createOrEditLabel" data-cy="LocatieCreateUpdateHeading">
            Create or edit a Locatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="locatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Adres" id="locatie-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField
                label="Heeftlocatie Put"
                id="locatie-heeftlocatiePut"
                data-cy="heeftlocatiePut"
                type="select"
                multiple
                name="heeftlocatiePuts"
              >
                <option value="" key="0" />
                {puts
                  ? puts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Wordtbegrensddoor Project"
                id="locatie-wordtbegrensddoorProject"
                data-cy="wordtbegrensddoorProject"
                type="select"
                multiple
                name="wordtbegrensddoorProjects"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Verzoek"
                id="locatie-betreftVerzoek"
                data-cy="betreftVerzoek"
                type="select"
                multiple
                name="betreftVerzoeks"
              >
                <option value="" key="0" />
                {verzoeks
                  ? verzoeks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Werkingsgebied Regeltekst"
                id="locatie-werkingsgebiedRegeltekst"
                data-cy="werkingsgebiedRegeltekst"
                type="select"
                multiple
                name="werkingsgebiedRegelteksts"
              >
                <option value="" key="0" />
                {regelteksts
                  ? regelteksts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Isverbondenmet Activiteit"
                id="locatie-isverbondenmetActiviteit"
                data-cy="isverbondenmetActiviteit"
                type="select"
                multiple
                name="isverbondenmetActiviteits"
              >
                <option value="" key="0" />
                {activiteits
                  ? activiteits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Verwijstnaar Gebiedsaanwijzing"
                id="locatie-verwijstnaarGebiedsaanwijzing"
                data-cy="verwijstnaarGebiedsaanwijzing"
                type="select"
                multiple
                name="verwijstnaarGebiedsaanwijzings"
              >
                <option value="" key="0" />
                {gebiedsaanwijzings
                  ? gebiedsaanwijzings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Geldtvoor Normwaarde"
                id="locatie-geldtvoorNormwaarde"
                data-cy="geldtvoorNormwaarde"
                type="select"
                multiple
                name="geldtvoorNormwaardes"
              >
                <option value="" key="0" />
                {normwaardes
                  ? normwaardes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/locatie" replace color="info">
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

export default LocatieUpdate;
