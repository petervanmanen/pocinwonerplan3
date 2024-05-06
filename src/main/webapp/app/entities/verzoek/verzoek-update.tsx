import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { IProjectactiviteit } from 'app/shared/model/projectactiviteit.model';
import { getEntities as getProjectactiviteits } from 'app/entities/projectactiviteit/projectactiviteit.reducer';
import { IProjectlocatie } from 'app/shared/model/projectlocatie.model';
import { getEntities as getProjectlocaties } from 'app/entities/projectlocatie/projectlocatie.reducer';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { getEntities as getActiviteits } from 'app/entities/activiteit/activiteit.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IInitiatiefnemer } from 'app/shared/model/initiatiefnemer.model';
import { getEntities as getInitiatiefnemers } from 'app/entities/initiatiefnemer/initiatiefnemer.reducer';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntity, updateEntity, createEntity, reset } from './verzoek.reducer';

export const VerzoekUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaks = useAppSelector(state => state.zaak.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const projectactiviteits = useAppSelector(state => state.projectactiviteit.entities);
  const projectlocaties = useAppSelector(state => state.projectlocatie.entities);
  const activiteits = useAppSelector(state => state.activiteit.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const initiatiefnemers = useAppSelector(state => state.initiatiefnemer.entities);
  const verzoekEntity = useAppSelector(state => state.verzoek.entity);
  const loading = useAppSelector(state => state.verzoek.loading);
  const updating = useAppSelector(state => state.verzoek.updating);
  const updateSuccess = useAppSelector(state => state.verzoek.updateSuccess);

  const handleClose = () => {
    navigate('/verzoek');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaks({}));
    dispatch(getVerzoeks({}));
    dispatch(getProjectactiviteits({}));
    dispatch(getProjectlocaties({}));
    dispatch(getActiviteits({}));
    dispatch(getLocaties({}));
    dispatch(getInitiatiefnemers({}));
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
      ...verzoekEntity,
      ...values,
      leidttotZaak: zaaks.find(it => it.id.toString() === values.leidttotZaak?.toString()),
      betrefteerderverzoekVerzoek: verzoeks.find(it => it.id.toString() === values.betrefteerderverzoekVerzoek?.toString()),
      betreftProjectactiviteits: mapIdList(values.betreftProjectactiviteits),
      betreftProjectlocaties: mapIdList(values.betreftProjectlocaties),
      betreftActiviteits: mapIdList(values.betreftActiviteits),
      betreftLocaties: mapIdList(values.betreftLocaties),
      heeftalsverantwoordelijkeInitiatiefnemer: initiatiefnemers.find(
        it => it.id.toString() === values.heeftalsverantwoordelijkeInitiatiefnemer?.toString(),
      ),
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
          ...verzoekEntity,
          leidttotZaak: verzoekEntity?.leidttotZaak?.id,
          betrefteerderverzoekVerzoek: verzoekEntity?.betrefteerderverzoekVerzoek?.id,
          betreftProjectactiviteits: verzoekEntity?.betreftProjectactiviteits?.map(e => e.id.toString()),
          betreftProjectlocaties: verzoekEntity?.betreftProjectlocaties?.map(e => e.id.toString()),
          betreftActiviteits: verzoekEntity?.betreftActiviteits?.map(e => e.id.toString()),
          betreftLocaties: verzoekEntity?.betreftLocaties?.map(e => e.id.toString()),
          heeftalsverantwoordelijkeInitiatiefnemer: verzoekEntity?.heeftalsverantwoordelijkeInitiatiefnemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verzoek.home.createOrEditLabel" data-cy="VerzoekCreateUpdateHeading">
            Create or edit a Verzoek
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="verzoek-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Akkoordverklaring"
                id="verzoek-akkoordverklaring"
                name="akkoordverklaring"
                data-cy="akkoordverklaring"
                check
                type="checkbox"
              />
              <ValidatedField label="Ambtshalve" id="verzoek-ambtshalve" name="ambtshalve" data-cy="ambtshalve" check type="checkbox" />
              <ValidatedField
                label="Datumindiening"
                id="verzoek-datumindiening"
                name="datumindiening"
                data-cy="datumindiening"
                type="date"
              />
              <ValidatedField label="Doel" id="verzoek-doel" name="doel" data-cy="doel" type="text" />
              <ValidatedField label="Naam" id="verzoek-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Referentieaanvrager"
                id="verzoek-referentieaanvrager"
                name="referentieaanvrager"
                data-cy="referentieaanvrager"
                type="text"
              />
              <ValidatedField
                label="Toelichtinglateraantelevereninformatie"
                id="verzoek-toelichtinglateraantelevereninformatie"
                name="toelichtinglateraantelevereninformatie"
                data-cy="toelichtinglateraantelevereninformatie"
                type="text"
              />
              <ValidatedField
                label="Toelichtingnietaantelevereninformatie"
                id="verzoek-toelichtingnietaantelevereninformatie"
                name="toelichtingnietaantelevereninformatie"
                data-cy="toelichtingnietaantelevereninformatie"
                type="text"
              />
              <ValidatedField
                label="Toelichtingverzoek"
                id="verzoek-toelichtingverzoek"
                name="toelichtingverzoek"
                data-cy="toelichtingverzoek"
                type="text"
              />
              <ValidatedField label="Type" id="verzoek-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Verzoeknummer" id="verzoek-verzoeknummer" name="verzoeknummer" data-cy="verzoeknummer" type="text" />
              <ValidatedField label="Volgnummer" id="verzoek-volgnummer" name="volgnummer" data-cy="volgnummer" type="text" />
              <ValidatedField id="verzoek-leidttotZaak" name="leidttotZaak" data-cy="leidttotZaak" label="Leidttot Zaak" type="select">
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="verzoek-betrefteerderverzoekVerzoek"
                name="betrefteerderverzoekVerzoek"
                data-cy="betrefteerderverzoekVerzoek"
                label="Betrefteerderverzoek Verzoek"
                type="select"
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
                label="Betreft Projectactiviteit"
                id="verzoek-betreftProjectactiviteit"
                data-cy="betreftProjectactiviteit"
                type="select"
                multiple
                name="betreftProjectactiviteits"
              >
                <option value="" key="0" />
                {projectactiviteits
                  ? projectactiviteits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Projectlocatie"
                id="verzoek-betreftProjectlocatie"
                data-cy="betreftProjectlocatie"
                type="select"
                multiple
                name="betreftProjectlocaties"
              >
                <option value="" key="0" />
                {projectlocaties
                  ? projectlocaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Activiteit"
                id="verzoek-betreftActiviteit"
                data-cy="betreftActiviteit"
                type="select"
                multiple
                name="betreftActiviteits"
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
                label="Betreft Locatie"
                id="verzoek-betreftLocatie"
                data-cy="betreftLocatie"
                type="select"
                multiple
                name="betreftLocaties"
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
                id="verzoek-heeftalsverantwoordelijkeInitiatiefnemer"
                name="heeftalsverantwoordelijkeInitiatiefnemer"
                data-cy="heeftalsverantwoordelijkeInitiatiefnemer"
                label="Heeftalsverantwoordelijke Initiatiefnemer"
                type="select"
                required
              >
                <option value="" key="0" />
                {initiatiefnemers
                  ? initiatiefnemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verzoek" replace color="info">
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

export default VerzoekUpdate;
