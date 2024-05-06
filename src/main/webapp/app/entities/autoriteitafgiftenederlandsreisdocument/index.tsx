import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Autoriteitafgiftenederlandsreisdocument from './autoriteitafgiftenederlandsreisdocument';
import AutoriteitafgiftenederlandsreisdocumentDetail from './autoriteitafgiftenederlandsreisdocument-detail';
import AutoriteitafgiftenederlandsreisdocumentUpdate from './autoriteitafgiftenederlandsreisdocument-update';
import AutoriteitafgiftenederlandsreisdocumentDeleteDialog from './autoriteitafgiftenederlandsreisdocument-delete-dialog';

const AutoriteitafgiftenederlandsreisdocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Autoriteitafgiftenederlandsreisdocument />} />
    <Route path="new" element={<AutoriteitafgiftenederlandsreisdocumentUpdate />} />
    <Route path=":id">
      <Route index element={<AutoriteitafgiftenederlandsreisdocumentDetail />} />
      <Route path="edit" element={<AutoriteitafgiftenederlandsreisdocumentUpdate />} />
      <Route path="delete" element={<AutoriteitafgiftenederlandsreisdocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AutoriteitafgiftenederlandsreisdocumentRoutes;
