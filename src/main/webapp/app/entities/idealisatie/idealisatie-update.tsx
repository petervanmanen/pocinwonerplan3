import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegeltekst } from 'app/shared/model/regeltekst.model';
import { getEntities as getRegelteksts } from 'app/entities/regeltekst/regeltekst.reducer';
import { IIdealisatie } from 'app/shared/model/idealisatie.model';
import { getEntity, updateEntity, createEntity, reset } from './idealisatie.reducer';

export const IdealisatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const regelteksts = useAppSelector(state => state.regeltekst.entities);
  const idealisatieEntity = useAppSelector(state => state.idealisatie.entity);
  const loading = useAppSelector(state => state.idealisatie.loading);
  const updating = useAppSelector(state => state.idealisatie.updating);
  const updateSuccess = useAppSelector(state => state.idealisatie.updateSuccess);

  const handleClose = () => {
    navigate('/idealisatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...idealisatieEntity,
      ...values,
      heeftidealisatieRegelteksts: mapIdList(values.heeftidealisatieRegelteksts),
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
          ...idealisatieEntity,
          heeftidealisatieRegelteksts: idealisatieEntity?.heeftidealisatieRegelteksts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.idealisatie.home.createOrEditLabel" data-cy="IdealisatieCreateUpdateHeading">
            Create or edit a Idealisatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="idealisatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="idealisatie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="idealisatie-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Heeftidealisatie Regeltekst"
                id="idealisatie-heeftidealisatieRegeltekst"
                data-cy="heeftidealisatieRegeltekst"
                type="select"
                multiple
                name="heeftidealisatieRegelteksts"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/idealisatie" replace color="info">
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

export default IdealisatieUpdate;
