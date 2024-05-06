import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBetrokkene } from 'app/shared/model/betrokkene.model';
import { getEntities as getBetrokkenes } from 'app/entities/betrokkene/betrokkene.reducer';
import { IKlantbeoordeling } from 'app/shared/model/klantbeoordeling.model';
import { getEntity, updateEntity, createEntity, reset } from './klantbeoordeling.reducer';

export const KlantbeoordelingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const betrokkenes = useAppSelector(state => state.betrokkene.entities);
  const klantbeoordelingEntity = useAppSelector(state => state.klantbeoordeling.entity);
  const loading = useAppSelector(state => state.klantbeoordeling.loading);
  const updating = useAppSelector(state => state.klantbeoordeling.updating);
  const updateSuccess = useAppSelector(state => state.klantbeoordeling.updateSuccess);

  const handleClose = () => {
    navigate('/klantbeoordeling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBetrokkenes({}));
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
      ...klantbeoordelingEntity,
      ...values,
      doetBetrokkene: betrokkenes.find(it => it.id.toString() === values.doetBetrokkene?.toString()),
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
          ...klantbeoordelingEntity,
          doetBetrokkene: klantbeoordelingEntity?.doetBetrokkene?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.klantbeoordeling.home.createOrEditLabel" data-cy="KlantbeoordelingCreateUpdateHeading">
            Create or edit a Klantbeoordeling
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
                <ValidatedField name="id" required readOnly id="klantbeoordeling-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beoordeling"
                id="klantbeoordeling-beoordeling"
                name="beoordeling"
                data-cy="beoordeling"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Categorie" id="klantbeoordeling-categorie" name="categorie" data-cy="categorie" type="text" />
              <ValidatedField
                label="Contactopnemen"
                id="klantbeoordeling-contactopnemen"
                name="contactopnemen"
                data-cy="contactopnemen"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Ddbeoordeling"
                id="klantbeoordeling-ddbeoordeling"
                name="ddbeoordeling"
                data-cy="ddbeoordeling"
                type="date"
              />
              <ValidatedField
                label="Kanaal"
                id="klantbeoordeling-kanaal"
                name="kanaal"
                data-cy="kanaal"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Onderwerp" id="klantbeoordeling-onderwerp" name="onderwerp" data-cy="onderwerp" type="text" />
              <ValidatedField
                label="Subcategorie"
                id="klantbeoordeling-subcategorie"
                name="subcategorie"
                data-cy="subcategorie"
                type="text"
              />
              <ValidatedField
                id="klantbeoordeling-doetBetrokkene"
                name="doetBetrokkene"
                data-cy="doetBetrokkene"
                label="Doet Betrokkene"
                type="select"
              >
                <option value="" key="0" />
                {betrokkenes
                  ? betrokkenes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/klantbeoordeling" replace color="info">
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

export default KlantbeoordelingUpdate;
