import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IProgrammasoort } from 'app/shared/model/programmasoort.model';
import { getEntities as getProgrammasoorts } from 'app/entities/programmasoort/programmasoort.reducer';
import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';
import { getEntities as getMuseumrelaties } from 'app/entities/museumrelatie/museumrelatie.reducer';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntities as getRaadsstuks } from 'app/entities/raadsstuk/raadsstuk.reducer';
import { IProgramma } from 'app/shared/model/programma.model';
import { getEntity, updateEntity, createEntity, reset } from './programma.reducer';

export const ProgrammaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const programmasoorts = useAppSelector(state => state.programmasoort.entities);
  const museumrelaties = useAppSelector(state => state.museumrelatie.entities);
  const raadsstuks = useAppSelector(state => state.raadsstuk.entities);
  const programmaEntity = useAppSelector(state => state.programma.entity);
  const loading = useAppSelector(state => state.programma.loading);
  const updating = useAppSelector(state => state.programma.updating);
  const updateSuccess = useAppSelector(state => state.programma.updateSuccess);

  const handleClose = () => {
    navigate('/programma');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKostenplaats({}));
    dispatch(getProgrammasoorts({}));
    dispatch(getMuseumrelaties({}));
    dispatch(getRaadsstuks({}));
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
      ...programmaEntity,
      ...values,
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
      voorProgrammasoorts: mapIdList(values.voorProgrammasoorts),
      voorMuseumrelatie: museumrelaties.find(it => it.id.toString() === values.voorMuseumrelatie?.toString()),
      hoortbijRaadsstuks: mapIdList(values.hoortbijRaadsstuks),
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
          ...programmaEntity,
          heeftKostenplaats: programmaEntity?.heeftKostenplaats?.id,
          voorProgrammasoorts: programmaEntity?.voorProgrammasoorts?.map(e => e.id.toString()),
          voorMuseumrelatie: programmaEntity?.voorMuseumrelatie?.id,
          hoortbijRaadsstuks: programmaEntity?.hoortbijRaadsstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.programma.home.createOrEditLabel" data-cy="ProgrammaCreateUpdateHeading">
            Create or edit a Programma
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="programma-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="programma-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                id="programma-heeftKostenplaats"
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
              <ValidatedField
                label="Voor Programmasoort"
                id="programma-voorProgrammasoort"
                data-cy="voorProgrammasoort"
                type="select"
                multiple
                name="voorProgrammasoorts"
              >
                <option value="" key="0" />
                {programmasoorts
                  ? programmasoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="programma-voorMuseumrelatie"
                name="voorMuseumrelatie"
                data-cy="voorMuseumrelatie"
                label="Voor Museumrelatie"
                type="select"
              >
                <option value="" key="0" />
                {museumrelaties
                  ? museumrelaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Hoortbij Raadsstuk"
                id="programma-hoortbijRaadsstuk"
                data-cy="hoortbijRaadsstuk"
                type="select"
                multiple
                name="hoortbijRaadsstuks"
              >
                <option value="" key="0" />
                {raadsstuks
                  ? raadsstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/programma" replace color="info">
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

export default ProgrammaUpdate;
