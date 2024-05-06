import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProgramma } from 'app/shared/model/programma.model';
import { getEntities as getProgrammas } from 'app/entities/programma/programma.reducer';
import { IProgrammasoort } from 'app/shared/model/programmasoort.model';
import { getEntity, updateEntity, createEntity, reset } from './programmasoort.reducer';

export const ProgrammasoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const programmas = useAppSelector(state => state.programma.entities);
  const programmasoortEntity = useAppSelector(state => state.programmasoort.entity);
  const loading = useAppSelector(state => state.programmasoort.loading);
  const updating = useAppSelector(state => state.programmasoort.updating);
  const updateSuccess = useAppSelector(state => state.programmasoort.updateSuccess);

  const handleClose = () => {
    navigate('/programmasoort');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProgrammas({}));
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
      ...programmasoortEntity,
      ...values,
      voorProgrammas: mapIdList(values.voorProgrammas),
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
          ...programmasoortEntity,
          voorProgrammas: programmasoortEntity?.voorProgrammas?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.programmasoort.home.createOrEditLabel" data-cy="ProgrammasoortCreateUpdateHeading">
            Create or edit a Programmasoort
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
                <ValidatedField name="id" required readOnly id="programmasoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="programmasoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="programmasoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Voor Programma"
                id="programmasoort-voorProgramma"
                data-cy="voorProgramma"
                type="select"
                multiple
                name="voorProgrammas"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/programmasoort" replace color="info">
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

export default ProgrammasoortUpdate;
