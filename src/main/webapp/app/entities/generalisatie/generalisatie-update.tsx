import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeneralisatie } from 'app/shared/model/generalisatie.model';
import { getEntity, updateEntity, createEntity, reset } from './generalisatie.reducer';

export const GeneralisatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const generalisatieEntity = useAppSelector(state => state.generalisatie.entity);
  const loading = useAppSelector(state => state.generalisatie.loading);
  const updating = useAppSelector(state => state.generalisatie.updating);
  const updateSuccess = useAppSelector(state => state.generalisatie.updateSuccess);

  const handleClose = () => {
    navigate('/generalisatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...generalisatieEntity,
      ...values,
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
          ...generalisatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.generalisatie.home.createOrEditLabel" data-cy="GeneralisatieCreateUpdateHeading">
            Create or edit a Generalisatie
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
                <ValidatedField name="id" required readOnly id="generalisatie-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumopname" id="generalisatie-datumopname" name="datumopname" data-cy="datumopname" type="date" />
              <ValidatedField label="Definitie" id="generalisatie-definitie" name="definitie" data-cy="definitie" type="text" />
              <ValidatedField label="Eaguid" id="generalisatie-eaguid" name="eaguid" data-cy="eaguid" type="text" />
              <ValidatedField label="Herkomst" id="generalisatie-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField
                label="Herkomstdefinitie"
                id="generalisatie-herkomstdefinitie"
                name="herkomstdefinitie"
                data-cy="herkomstdefinitie"
                type="text"
              />
              <ValidatedField
                label="Indicatiematerielehistorie"
                id="generalisatie-indicatiematerielehistorie"
                name="indicatiematerielehistorie"
                data-cy="indicatiematerielehistorie"
                check
                type="checkbox"
              />
              <ValidatedField label="Naam" id="generalisatie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Toelichting" id="generalisatie-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/generalisatie" replace color="info">
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

export default GeneralisatieUpdate;
