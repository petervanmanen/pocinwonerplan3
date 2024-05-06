import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getActiviteits } from 'app/entities/activiteit/activiteit.reducer';
import { IRondleiding } from 'app/shared/model/rondleiding.model';
import { getEntities as getRondleidings } from 'app/entities/rondleiding/rondleiding.reducer';
import { IActiviteitsoort } from 'app/shared/model/activiteitsoort.model';
import { getEntities as getActiviteitsoorts } from 'app/entities/activiteitsoort/activiteitsoort.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IProgramma } from 'app/shared/model/programma.model';
import { getEntities as getProgrammas } from 'app/entities/programma/programma.reducer';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { getEntity, updateEntity, createEntity, reset } from './activiteit.reducer';

export const ActiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activiteits = useAppSelector(state => state.activiteit.entities);
  const rondleidings = useAppSelector(state => state.rondleiding.entities);
  const activiteitsoorts = useAppSelector(state => state.activiteitsoort.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const programmas = useAppSelector(state => state.programma.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const activiteitEntity = useAppSelector(state => state.activiteit.entity);
  const loading = useAppSelector(state => state.activiteit.loading);
  const updating = useAppSelector(state => state.activiteit.updating);
  const updateSuccess = useAppSelector(state => state.activiteit.updateSuccess);

  const handleClose = () => {
    navigate('/activiteit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActiviteits({}));
    dispatch(getRondleidings({}));
    dispatch(getActiviteitsoorts({}));
    dispatch(getLocaties({}));
    dispatch(getProgrammas({}));
    dispatch(getVerzoeks({}));
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
      ...activiteitEntity,
      ...values,
      gerelateerdeactiviteitActiviteit: activiteits.find(it => it.id.toString() === values.gerelateerdeactiviteitActiviteit?.toString()),
      bovenliggendeactiviteitActiviteit: activiteits.find(it => it.id.toString() === values.bovenliggendeactiviteitActiviteit?.toString()),
      heeftRondleiding: rondleidings.find(it => it.id.toString() === values.heeftRondleiding?.toString()),
      vansoortActiviteitsoort: activiteitsoorts.find(it => it.id.toString() === values.vansoortActiviteitsoort?.toString()),
      isverbondenmetLocaties: mapIdList(values.isverbondenmetLocaties),
      bestaatuitActiviteit2: activiteits.find(it => it.id.toString() === values.bestaatuitActiviteit2?.toString()),
      bestaatuitProgramma: programmas.find(it => it.id.toString() === values.bestaatuitProgramma?.toString()),
      betreftVerzoeks: mapIdList(values.betreftVerzoeks),
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
          ...activiteitEntity,
          gerelateerdeactiviteitActiviteit: activiteitEntity?.gerelateerdeactiviteitActiviteit?.id,
          bovenliggendeactiviteitActiviteit: activiteitEntity?.bovenliggendeactiviteitActiviteit?.id,
          heeftRondleiding: activiteitEntity?.heeftRondleiding?.id,
          vansoortActiviteitsoort: activiteitEntity?.vansoortActiviteitsoort?.id,
          isverbondenmetLocaties: activiteitEntity?.isverbondenmetLocaties?.map(e => e.id.toString()),
          bestaatuitActiviteit2: activiteitEntity?.bestaatuitActiviteit2?.id,
          bestaatuitProgramma: activiteitEntity?.bestaatuitProgramma?.id,
          betreftVerzoeks: activiteitEntity?.betreftVerzoeks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.activiteit.home.createOrEditLabel" data-cy="ActiviteitCreateUpdateHeading">
            Create or edit a Activiteit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="activiteit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Omschrijving" id="activiteit-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="activiteit-gerelateerdeactiviteitActiviteit"
                name="gerelateerdeactiviteitActiviteit"
                data-cy="gerelateerdeactiviteitActiviteit"
                label="Gerelateerdeactiviteit Activiteit"
                type="select"
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
                id="activiteit-bovenliggendeactiviteitActiviteit"
                name="bovenliggendeactiviteitActiviteit"
                data-cy="bovenliggendeactiviteitActiviteit"
                label="Bovenliggendeactiviteit Activiteit"
                type="select"
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
                id="activiteit-heeftRondleiding"
                name="heeftRondleiding"
                data-cy="heeftRondleiding"
                label="Heeft Rondleiding"
                type="select"
              >
                <option value="" key="0" />
                {rondleidings
                  ? rondleidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="activiteit-vansoortActiviteitsoort"
                name="vansoortActiviteitsoort"
                data-cy="vansoortActiviteitsoort"
                label="Vansoort Activiteitsoort"
                type="select"
              >
                <option value="" key="0" />
                {activiteitsoorts
                  ? activiteitsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Isverbondenmet Locatie"
                id="activiteit-isverbondenmetLocatie"
                data-cy="isverbondenmetLocatie"
                type="select"
                multiple
                name="isverbondenmetLocaties"
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
                id="activiteit-bestaatuitActiviteit2"
                name="bestaatuitActiviteit2"
                data-cy="bestaatuitActiviteit2"
                label="Bestaatuit Activiteit 2"
                type="select"
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
                id="activiteit-bestaatuitProgramma"
                name="bestaatuitProgramma"
                data-cy="bestaatuitProgramma"
                label="Bestaatuit Programma"
                type="select"
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
                label="Betreft Verzoek"
                id="activiteit-betreftVerzoek"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activiteit" replace color="info">
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

export default ActiviteitUpdate;
