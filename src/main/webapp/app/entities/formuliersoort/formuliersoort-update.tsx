import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntities as getZaaktypes } from 'app/entities/zaaktype/zaaktype.reducer';
import { IFormuliersoort } from 'app/shared/model/formuliersoort.model';
import { getEntity, updateEntity, createEntity, reset } from './formuliersoort.reducer';

export const FormuliersoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaktypes = useAppSelector(state => state.zaaktype.entities);
  const formuliersoortEntity = useAppSelector(state => state.formuliersoort.entity);
  const loading = useAppSelector(state => state.formuliersoort.loading);
  const updating = useAppSelector(state => state.formuliersoort.updating);
  const updateSuccess = useAppSelector(state => state.formuliersoort.updateSuccess);

  const handleClose = () => {
    navigate('/formuliersoort');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaktypes({}));
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
      ...formuliersoortEntity,
      ...values,
      isaanleidingvoorZaaktypes: mapIdList(values.isaanleidingvoorZaaktypes),
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
          ...formuliersoortEntity,
          isaanleidingvoorZaaktypes: formuliersoortEntity?.isaanleidingvoorZaaktypes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.formuliersoort.home.createOrEditLabel" data-cy="FormuliersoortCreateUpdateHeading">
            Create or edit a Formuliersoort
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
                <ValidatedField name="id" required readOnly id="formuliersoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Ingebruik" id="formuliersoort-ingebruik" name="ingebruik" data-cy="ingebruik" type="text" />
              <ValidatedField label="Naam" id="formuliersoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Onderwerp" id="formuliersoort-onderwerp" name="onderwerp" data-cy="onderwerp" type="text" />
              <ValidatedField
                label="Isaanleidingvoor Zaaktype"
                id="formuliersoort-isaanleidingvoorZaaktype"
                data-cy="isaanleidingvoorZaaktype"
                type="select"
                multiple
                name="isaanleidingvoorZaaktypes"
              >
                <option value="" key="0" />
                {zaaktypes
                  ? zaaktypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/formuliersoort" replace color="info">
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

export default FormuliersoortUpdate;
