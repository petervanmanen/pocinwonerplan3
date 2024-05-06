import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntity, updateEntity, createEntity, reset } from './leverancier.reducer';

export const LeverancierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.categorie.entities);
  const leverancierEntity = useAppSelector(state => state.leverancier.entity);
  const loading = useAppSelector(state => state.leverancier.loading);
  const updating = useAppSelector(state => state.leverancier.updating);
  const updateSuccess = useAppSelector(state => state.leverancier.updateSuccess);

  const handleClose = () => {
    navigate('/leverancier');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
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
      ...leverancierEntity,
      ...values,
      gekwalificeerdCategories: mapIdList(values.gekwalificeerdCategories),
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
          ...leverancierEntity,
          gekwalificeerdCategories: leverancierEntity?.gekwalificeerdCategories?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.leverancier.home.createOrEditLabel" data-cy="LeverancierCreateUpdateHeading">
            Create or edit a Leverancier
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="leverancier-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Agbcode" id="leverancier-agbcode" name="agbcode" data-cy="agbcode" type="text" />
              <ValidatedField
                label="Leverancierscode"
                id="leverancier-leverancierscode"
                name="leverancierscode"
                data-cy="leverancierscode"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="leverancier-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Soortleverancier"
                id="leverancier-soortleverancier"
                name="soortleverancier"
                data-cy="soortleverancier"
                type="text"
              />
              <ValidatedField
                label="Soortleveranciercode"
                id="leverancier-soortleveranciercode"
                name="soortleveranciercode"
                data-cy="soortleveranciercode"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Gekwalificeerd Categorie"
                id="leverancier-gekwalificeerdCategorie"
                data-cy="gekwalificeerdCategorie"
                type="select"
                multiple
                name="gekwalificeerdCategories"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leverancier" replace color="info">
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

export default LeverancierUpdate;
