import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResultaat } from 'app/shared/model/resultaat.model';
import { getEntities as getResultaats } from 'app/entities/resultaat/resultaat.reducer';
import { IUitstroomreden } from 'app/shared/model/uitstroomreden.model';
import { getEntities as getUitstroomredens } from 'app/entities/uitstroomreden/uitstroomreden.reducer';
import { ITrajectsoort } from 'app/shared/model/trajectsoort.model';
import { getEntities as getTrajectsoorts } from 'app/entities/trajectsoort/trajectsoort.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { ITraject } from 'app/shared/model/traject.model';
import { getEntity, updateEntity, createEntity, reset } from './traject.reducer';

export const TrajectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const resultaats = useAppSelector(state => state.resultaat.entities);
  const uitstroomredens = useAppSelector(state => state.uitstroomreden.entities);
  const trajectsoorts = useAppSelector(state => state.trajectsoort.entities);
  const clients = useAppSelector(state => state.client.entities);
  const trajectEntity = useAppSelector(state => state.traject.entity);
  const loading = useAppSelector(state => state.traject.loading);
  const updating = useAppSelector(state => state.traject.updating);
  const updateSuccess = useAppSelector(state => state.traject.updateSuccess);

  const handleClose = () => {
    navigate('/traject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getResultaats({}));
    dispatch(getUitstroomredens({}));
    dispatch(getTrajectsoorts({}));
    dispatch(getClients({}));
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
      ...trajectEntity,
      ...values,
      heeftresultaatResultaat: resultaats.find(it => it.id.toString() === values.heeftresultaatResultaat?.toString()),
      heeftuitstroomredenUitstroomreden: uitstroomredens.find(
        it => it.id.toString() === values.heeftuitstroomredenUitstroomreden?.toString(),
      ),
      istrajectsoortTrajectsoort: trajectsoorts.find(it => it.id.toString() === values.istrajectsoortTrajectsoort?.toString()),
      heeftparticipatietrajectClient: clients.find(it => it.id.toString() === values.heeftparticipatietrajectClient?.toString()),
      heefttrajectClient: clients.find(it => it.id.toString() === values.heefttrajectClient?.toString()),
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
          ...trajectEntity,
          heeftresultaatResultaat: trajectEntity?.heeftresultaatResultaat?.id,
          heeftuitstroomredenUitstroomreden: trajectEntity?.heeftuitstroomredenUitstroomreden?.id,
          istrajectsoortTrajectsoort: trajectEntity?.istrajectsoortTrajectsoort?.id,
          heeftparticipatietrajectClient: trajectEntity?.heeftparticipatietrajectClient?.id,
          heefttrajectClient: trajectEntity?.heefttrajectClient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.traject.home.createOrEditLabel" data-cy="TrajectCreateUpdateHeading">
            Create or edit a Traject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="traject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumeinde" id="traject-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="traject-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumtoekenning"
                id="traject-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="text"
              />
              <ValidatedField label="Naam" id="traject-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="traject-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Resultaat" id="traject-resultaat" name="resultaat" data-cy="resultaat" type="text" />
              <ValidatedField
                id="traject-heeftresultaatResultaat"
                name="heeftresultaatResultaat"
                data-cy="heeftresultaatResultaat"
                label="Heeftresultaat Resultaat"
                type="select"
                required
              >
                <option value="" key="0" />
                {resultaats
                  ? resultaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="traject-heeftuitstroomredenUitstroomreden"
                name="heeftuitstroomredenUitstroomreden"
                data-cy="heeftuitstroomredenUitstroomreden"
                label="Heeftuitstroomreden Uitstroomreden"
                type="select"
                required
              >
                <option value="" key="0" />
                {uitstroomredens
                  ? uitstroomredens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="traject-istrajectsoortTrajectsoort"
                name="istrajectsoortTrajectsoort"
                data-cy="istrajectsoortTrajectsoort"
                label="Istrajectsoort Trajectsoort"
                type="select"
              >
                <option value="" key="0" />
                {trajectsoorts
                  ? trajectsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="traject-heeftparticipatietrajectClient"
                name="heeftparticipatietrajectClient"
                data-cy="heeftparticipatietrajectClient"
                label="Heeftparticipatietraject Client"
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
                id="traject-heefttrajectClient"
                name="heefttrajectClient"
                data-cy="heefttrajectClient"
                label="Heefttraject Client"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/traject" replace color="info">
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

export default TrajectUpdate;
