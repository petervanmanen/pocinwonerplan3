import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGegeven } from 'app/shared/model/gegeven.model';
import { getEntities as getGegevens } from 'app/entities/gegeven/gegeven.reducer';
import { IClassificatie } from 'app/shared/model/classificatie.model';
import { getEntity, updateEntity, createEntity, reset } from './classificatie.reducer';

export const ClassificatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gegevens = useAppSelector(state => state.gegeven.entities);
  const classificatieEntity = useAppSelector(state => state.classificatie.entity);
  const loading = useAppSelector(state => state.classificatie.loading);
  const updating = useAppSelector(state => state.classificatie.updating);
  const updateSuccess = useAppSelector(state => state.classificatie.updateSuccess);

  const handleClose = () => {
    navigate('/classificatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGegevens({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...classificatieEntity,
      ...values,
      geclassificeerdalsGegevens: mapIdList(values.geclassificeerdalsGegevens),
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
          ...classificatieEntity,
          geclassificeerdalsGegevens: classificatieEntity?.geclassificeerdalsGegevens?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.classificatie.home.createOrEditLabel" data-cy="ClassificatieCreateUpdateHeading">
            Create or edit a Classificatie
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
                <ValidatedField name="id" required readOnly id="classificatie-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bevatpersoonsgegevens"
                id="classificatie-bevatpersoonsgegevens"
                name="bevatpersoonsgegevens"
                data-cy="bevatpersoonsgegevens"
                type="text"
              />
              <ValidatedField
                label="Gerelateerdpersoonsgegevens"
                id="classificatie-gerelateerdpersoonsgegevens"
                name="gerelateerdpersoonsgegevens"
                data-cy="gerelateerdpersoonsgegevens"
                type="text"
              />
              <ValidatedField
                label="Geclassificeerdals Gegeven"
                id="classificatie-geclassificeerdalsGegeven"
                data-cy="geclassificeerdalsGegeven"
                type="select"
                multiple
                name="geclassificeerdalsGegevens"
              >
                <option value="" key="0" />
                {gegevens
                  ? gegevens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/classificatie" replace color="info">
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

export default ClassificatieUpdate;
