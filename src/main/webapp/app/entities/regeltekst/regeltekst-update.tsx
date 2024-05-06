import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IThema } from 'app/shared/model/thema.model';
import { getEntities as getThemas } from 'app/entities/thema/thema.reducer';
import { IIdealisatie } from 'app/shared/model/idealisatie.model';
import { getEntities as getIdealisaties } from 'app/entities/idealisatie/idealisatie.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IOmgevingsdocument } from 'app/shared/model/omgevingsdocument.model';
import { getEntities as getOmgevingsdocuments } from 'app/entities/omgevingsdocument/omgevingsdocument.reducer';
import { getEntities as getRegelteksts } from 'app/entities/regeltekst/regeltekst.reducer';
import { IRegeltekst } from 'app/shared/model/regeltekst.model';
import { getEntity, updateEntity, createEntity, reset } from './regeltekst.reducer';

export const RegeltekstUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const themas = useAppSelector(state => state.thema.entities);
  const idealisaties = useAppSelector(state => state.idealisatie.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const omgevingsdocuments = useAppSelector(state => state.omgevingsdocument.entities);
  const regelteksts = useAppSelector(state => state.regeltekst.entities);
  const regeltekstEntity = useAppSelector(state => state.regeltekst.entity);
  const loading = useAppSelector(state => state.regeltekst.loading);
  const updating = useAppSelector(state => state.regeltekst.updating);
  const updateSuccess = useAppSelector(state => state.regeltekst.updateSuccess);

  const handleClose = () => {
    navigate('/regeltekst');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getThemas({}));
    dispatch(getIdealisaties({}));
    dispatch(getLocaties({}));
    dispatch(getOmgevingsdocuments({}));
    dispatch(getRegelteksts({}));
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
      ...regeltekstEntity,
      ...values,
      heeftthemaThemas: mapIdList(values.heeftthemaThemas),
      heeftidealisatieIdealisaties: mapIdList(values.heeftidealisatieIdealisaties),
      werkingsgebiedLocaties: mapIdList(values.werkingsgebiedLocaties),
      bevatOmgevingsdocument: omgevingsdocuments.find(it => it.id.toString() === values.bevatOmgevingsdocument?.toString()),
      werkingsgebiedRegeltekst2: regelteksts.find(it => it.id.toString() === values.werkingsgebiedRegeltekst2?.toString()),
      isgerelateerdRegeltekst2: regelteksts.find(it => it.id.toString() === values.isgerelateerdRegeltekst2?.toString()),
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
          ...regeltekstEntity,
          heeftthemaThemas: regeltekstEntity?.heeftthemaThemas?.map(e => e.id.toString()),
          heeftidealisatieIdealisaties: regeltekstEntity?.heeftidealisatieIdealisaties?.map(e => e.id.toString()),
          werkingsgebiedLocaties: regeltekstEntity?.werkingsgebiedLocaties?.map(e => e.id.toString()),
          bevatOmgevingsdocument: regeltekstEntity?.bevatOmgevingsdocument?.id,
          werkingsgebiedRegeltekst2: regeltekstEntity?.werkingsgebiedRegeltekst2?.id,
          isgerelateerdRegeltekst2: regeltekstEntity?.isgerelateerdRegeltekst2?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.regeltekst.home.createOrEditLabel" data-cy="RegeltekstCreateUpdateHeading">
            Create or edit a Regeltekst
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="regeltekst-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Identificatie"
                id="regeltekst-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField label="Omschrijving" id="regeltekst-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Tekst" id="regeltekst-tekst" name="tekst" data-cy="tekst" type="text" />
              <ValidatedField
                label="Heeftthema Thema"
                id="regeltekst-heeftthemaThema"
                data-cy="heeftthemaThema"
                type="select"
                multiple
                name="heeftthemaThemas"
              >
                <option value="" key="0" />
                {themas
                  ? themas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftidealisatie Idealisatie"
                id="regeltekst-heeftidealisatieIdealisatie"
                data-cy="heeftidealisatieIdealisatie"
                type="select"
                multiple
                name="heeftidealisatieIdealisaties"
              >
                <option value="" key="0" />
                {idealisaties
                  ? idealisaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Werkingsgebied Locatie"
                id="regeltekst-werkingsgebiedLocatie"
                data-cy="werkingsgebiedLocatie"
                type="select"
                multiple
                name="werkingsgebiedLocaties"
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
                id="regeltekst-bevatOmgevingsdocument"
                name="bevatOmgevingsdocument"
                data-cy="bevatOmgevingsdocument"
                label="Bevat Omgevingsdocument"
                type="select"
                required
              >
                <option value="" key="0" />
                {omgevingsdocuments
                  ? omgevingsdocuments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="regeltekst-werkingsgebiedRegeltekst2"
                name="werkingsgebiedRegeltekst2"
                data-cy="werkingsgebiedRegeltekst2"
                label="Werkingsgebied Regeltekst 2"
                type="select"
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
                id="regeltekst-isgerelateerdRegeltekst2"
                name="isgerelateerdRegeltekst2"
                data-cy="isgerelateerdRegeltekst2"
                label="Isgerelateerd Regeltekst 2"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/regeltekst" replace color="info">
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

export default RegeltekstUpdate;
