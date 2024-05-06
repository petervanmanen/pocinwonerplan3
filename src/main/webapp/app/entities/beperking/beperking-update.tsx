import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeperkingscategorie } from 'app/shared/model/beperkingscategorie.model';
import { getEntities as getBeperkingscategories } from 'app/entities/beperkingscategorie/beperkingscategorie.reducer';
import { IBeschikking } from 'app/shared/model/beschikking.model';
import { getEntities as getBeschikkings } from 'app/entities/beschikking/beschikking.reducer';
import { IBeperking } from 'app/shared/model/beperking.model';
import { getEntity, updateEntity, createEntity, reset } from './beperking.reducer';

export const BeperkingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beperkingscategories = useAppSelector(state => state.beperkingscategorie.entities);
  const beschikkings = useAppSelector(state => state.beschikking.entities);
  const beperkingEntity = useAppSelector(state => state.beperking.entity);
  const loading = useAppSelector(state => state.beperking.loading);
  const updating = useAppSelector(state => state.beperking.updating);
  const updateSuccess = useAppSelector(state => state.beperking.updateSuccess);

  const handleClose = () => {
    navigate('/beperking');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBeperkingscategories({}));
    dispatch(getBeschikkings({}));
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
      ...beperkingEntity,
      ...values,
      iseenBeperkingscategorie: beperkingscategories.find(it => it.id.toString() === values.iseenBeperkingscategorie?.toString()),
      isgebaseerdopBeschikking: beschikkings.find(it => it.id.toString() === values.isgebaseerdopBeschikking?.toString()),
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
          ...beperkingEntity,
          iseenBeperkingscategorie: beperkingEntity?.iseenBeperkingscategorie?.id,
          isgebaseerdopBeschikking: beperkingEntity?.isgebaseerdopBeschikking?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beperking.home.createOrEditLabel" data-cy="BeperkingCreateUpdateHeading">
            Create or edit a Beperking
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beperking-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Categorie" id="beperking-categorie" name="categorie" data-cy="categorie" type="text" />
              <ValidatedField label="Commentaar" id="beperking-commentaar" name="commentaar" data-cy="commentaar" type="text" />
              <ValidatedField label="Duur" id="beperking-duur" name="duur" data-cy="duur" type="text" />
              <ValidatedField label="Wet" id="beperking-wet" name="wet" data-cy="wet" type="text" />
              <ValidatedField
                id="beperking-iseenBeperkingscategorie"
                name="iseenBeperkingscategorie"
                data-cy="iseenBeperkingscategorie"
                label="Iseen Beperkingscategorie"
                type="select"
              >
                <option value="" key="0" />
                {beperkingscategories
                  ? beperkingscategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="beperking-isgebaseerdopBeschikking"
                name="isgebaseerdopBeschikking"
                data-cy="isgebaseerdopBeschikking"
                label="Isgebaseerdop Beschikking"
                type="select"
              >
                <option value="" key="0" />
                {beschikkings
                  ? beschikkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beperking" replace color="info">
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

export default BeperkingUpdate;
