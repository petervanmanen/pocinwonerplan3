import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ingeschrevenpersoon from './ingeschrevenpersoon';
import IngeschrevenpersoonDetail from './ingeschrevenpersoon-detail';
import IngeschrevenpersoonUpdate from './ingeschrevenpersoon-update';
import IngeschrevenpersoonDeleteDialog from './ingeschrevenpersoon-delete-dialog';

const IngeschrevenpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ingeschrevenpersoon />} />
    <Route path="new" element={<IngeschrevenpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<IngeschrevenpersoonDetail />} />
      <Route path="edit" element={<IngeschrevenpersoonUpdate />} />
      <Route path="delete" element={<IngeschrevenpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IngeschrevenpersoonRoutes;
