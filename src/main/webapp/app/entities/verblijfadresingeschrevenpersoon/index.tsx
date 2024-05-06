import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfadresingeschrevenpersoon from './verblijfadresingeschrevenpersoon';
import VerblijfadresingeschrevenpersoonDetail from './verblijfadresingeschrevenpersoon-detail';
import VerblijfadresingeschrevenpersoonUpdate from './verblijfadresingeschrevenpersoon-update';
import VerblijfadresingeschrevenpersoonDeleteDialog from './verblijfadresingeschrevenpersoon-delete-dialog';

const VerblijfadresingeschrevenpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfadresingeschrevenpersoon />} />
    <Route path="new" element={<VerblijfadresingeschrevenpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfadresingeschrevenpersoonDetail />} />
      <Route path="edit" element={<VerblijfadresingeschrevenpersoonUpdate />} />
      <Route path="delete" element={<VerblijfadresingeschrevenpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfadresingeschrevenpersoonRoutes;
