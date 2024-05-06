import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFunctie } from 'app/shared/model/functie.model';
import { getEntities as getFuncties } from 'app/entities/functie/functie.reducer';
import { IOpdrachtnemer } from 'app/shared/model/opdrachtnemer.model';
import { getEntity, updateEntity, createEntity, reset } from './opdrachtnemer.reducer';

export const OpdrachtnemerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const functies = useAppSelector(state => state.functie.entities);
  const opdrachtnemerEntity = useAppSelector(state => state.opdrachtnemer.entity);
  const loading = useAppSelector(state => state.opdrachtnemer.loading);
  const updating = useAppSelector(state => state.opdrachtnemer.updating);
  const updateSuccess = useAppSelector(state => state.opdrachtnemer.updateSuccess);

  const handleClose = () => {
    navigate('/opdrachtnemer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFuncties({}));
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
      ...opdrachtnemerEntity,
      ...values,
      uitgevoerddoorFunctie: functies.find(it => it.id.toString() === values.uitgevoerddoorFunctie?.toString()),
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
          ...opdrachtnemerEntity,
          uitgevoerddoorFunctie: opdrachtnemerEntity?.uitgevoerddoorFunctie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.opdrachtnemer.home.createOrEditLabel" data-cy="OpdrachtnemerCreateUpdateHeading">
            Create or edit a Opdrachtnemer
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
                <ValidatedField name="id" required readOnly id="opdrachtnemer-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Clustercode"
                id="opdrachtnemer-clustercode"
                name="clustercode"
                data-cy="clustercode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Clustercodeomschrijving"
                id="opdrachtnemer-clustercodeomschrijving"
                name="clustercodeomschrijving"
                data-cy="clustercodeomschrijving"
                type="text"
              />
              <ValidatedField label="Naam" id="opdrachtnemer-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="opdrachtnemer-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="opdrachtnemer-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="opdrachtnemer-uitgevoerddoorFunctie"
                name="uitgevoerddoorFunctie"
                data-cy="uitgevoerddoorFunctie"
                label="Uitgevoerddoor Functie"
                type="select"
              >
                <option value="" key="0" />
                {functies
                  ? functies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opdrachtnemer" replace color="info">
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

export default OpdrachtnemerUpdate;
